package io.github.msaggik.weatherforecastapp.presentation.viewmodel.state

import io.github.msaggik.weatherforecastapp.domain.models.Forecast

internal sealed interface ForecastState {

    data object Loading : ForecastState

    data class Content(val forecast: Forecast) : ForecastState

    data object InternetOff : ForecastState

    data object InternetError : ForecastState
}
