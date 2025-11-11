package io.github.msaggik.weatherforecastapp.domain.repository

import io.github.msaggik.weatherforecastapp.domain.models.Forecast
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.flow.Flow

internal interface WeatherBackendRepository {
    fun getWeather(
        location: String,
        days: Int
    ): Flow<NetworkState<Forecast>>
}
