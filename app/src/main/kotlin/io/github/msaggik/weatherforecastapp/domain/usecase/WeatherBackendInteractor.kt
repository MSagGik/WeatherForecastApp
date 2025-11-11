package io.github.msaggik.weatherforecastapp.domain.usecase

import io.github.msaggik.weatherforecastapp.domain.models.Forecast
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.flow.Flow

interface WeatherBackendInteractor {
    fun getWeather(
        location: String,
        days: Int
    ): Flow<NetworkState<Forecast>>
}
