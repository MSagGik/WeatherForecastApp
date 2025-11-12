package io.github.msaggik.weatherforecastapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.msaggik.weatherforecastapp.domain.usecase.WeatherBackendInteractor
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.state.ForecastState
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_NUMBER_DAY_WEATHER = 3

internal class WeatherViewModel(
    private val weatherBackendInteractor: WeatherBackendInteractor
) : ViewModel() {

    private val _forecastState = MutableStateFlow<ForecastState>(ForecastState.Loading)
    val forecastState: StateFlow<ForecastState> = _forecastState.asStateFlow()

    init {
        // example settings
        loadWeather("Moscow", DEFAULT_NUMBER_DAY_WEATHER)
    }

    fun loadWeather(location: String, days: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _forecastState.emit(ForecastState.Loading)
            weatherBackendInteractor.getWeather(location, days)
                .collect { response ->
                    _forecastState.emit(
                        when (response) {
                            is NetworkState.Loading -> ForecastState.Loading
                            is NetworkState.Success -> ForecastState.Content(response.data)
                            is NetworkState.InternetOff -> ForecastState.InternetOff
                            is NetworkState.InternetError -> ForecastState.InternetError
                        }
                    )
                }
        }
    }
}
