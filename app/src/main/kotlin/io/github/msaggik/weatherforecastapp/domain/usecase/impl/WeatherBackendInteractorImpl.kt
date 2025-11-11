package io.github.msaggik.weatherforecastapp.domain.usecase.impl

import io.github.msaggik.weatherforecastapp.domain.models.Forecast
import io.github.msaggik.weatherforecastapp.domain.repository.WeatherBackendRepository
import io.github.msaggik.weatherforecastapp.domain.usecase.WeatherBackendInteractor
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.flow.Flow

internal class WeatherBackendInteractorImpl(
    private val weatherBackendRepository: WeatherBackendRepository
) : WeatherBackendInteractor {

    override fun getWeather(
        location: String,
        days: Int
    ): Flow<NetworkState<Forecast>> = weatherBackendRepository.getWeather(location = location, days = days)
}
