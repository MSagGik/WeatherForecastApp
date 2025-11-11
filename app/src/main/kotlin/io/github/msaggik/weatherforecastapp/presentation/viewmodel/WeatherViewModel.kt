package io.github.msaggik.weatherforecastapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.msaggik.weatherforecastapp.domain.usecase.WeatherBackendInteractor
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.state.ForecastState
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class WeatherViewModel(
    private val weatherBackendInteractor: WeatherBackendInteractor
) : ViewModel() {

    private var _forecastStateLiveData = MutableLiveData<ForecastState>()
    val forecastStateLiveData: LiveData<ForecastState> = _forecastStateLiveData

    init {
        _forecastStateLiveData.value = ForecastState.Loading
    }

    fun getWeather(
        location: String,
        days: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _forecastStateLiveData.postValue(ForecastState.Loading)

            weatherBackendInteractor.getWeather(
                location = location,
                days = days
            ).collect { response ->
                when (response) {
                    is NetworkState.Loading -> {
                        _forecastStateLiveData.postValue(ForecastState.Loading)
                    }

                    is NetworkState.Success -> {
                        _forecastStateLiveData.postValue(ForecastState.Content(response.data))
                    }

                    is NetworkState.InternetOff -> {
                        _forecastStateLiveData.postValue(ForecastState.InternetOff)
                    }

                    is NetworkState.InternetError -> {
                        _forecastStateLiveData.postValue(ForecastState.InternetError)
                    }
                }
            }
        }
    }
}
