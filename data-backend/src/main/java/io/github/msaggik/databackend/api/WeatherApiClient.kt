package io.github.msaggik.databackend.api

import io.github.msaggik.databackend.api.dto.ResponseBackend

interface WeatherApiClient {
    suspend fun doRequestGetWeather(
        location: String,
        days: Int
    ): ResponseBackend
}
