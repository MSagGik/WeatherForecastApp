package io.github.msaggik.databackend.api.backend

import io.github.msaggik.databackend.BuildConfig
import io.github.msaggik.databackend.api.dto.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY

internal interface WeatherApiService {

    @GET("/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String = WEATHER_API_KEY,
        @Query("q") location: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): ForecastResponse
}
