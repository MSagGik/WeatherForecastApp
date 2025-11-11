package io.github.msaggik.weatherforecastapp.data.repositoryimpl.backend

import io.github.msaggik.databackend.api.WeatherApiClient
import io.github.msaggik.databackend.api.dto.ForecastResponse
import io.github.msaggik.databackend.api.dto.ResponseBackend
import io.github.msaggik.weatherforecastapp.data.mappers.WeatherMapper
import io.github.msaggik.weatherforecastapp.domain.models.Forecast
import io.github.msaggik.weatherforecastapp.domain.repository.WeatherBackendRepository
import io.github.msaggik.weatherforecastapp.utils.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherBackendRepositoryImpl(
    private val weatherApiClient: WeatherApiClient,
    private val weatherMapper: WeatherMapper
) : WeatherBackendRepository {

    override fun getWeather(
        location: String,
        days: Int
    ): Flow<NetworkState<Forecast>> = flow {
        val response = weatherApiClient.doRequestGetWeather(
            location = location,
            days = days
        )
        when(response.resultNetworkCode) {
            ResponseBackend.DEFAULT -> { emit(NetworkState.Loading) }
            ResponseBackend.SUCCESS_OK -> { emit(NetworkState.Success(weatherMapper.map(response as ForecastResponse))) }
            ResponseBackend.ERROR_NO_INTERNET -> { emit(NetworkState.InternetOff) }
            ResponseBackend.ERROR_UNKNOWN -> { emit(NetworkState.InternetError) }
        }
    }
}
