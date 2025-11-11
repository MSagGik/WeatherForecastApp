package io.github.msaggik.weatherforecastapp.domain.models.entities

data class ForecastDay(
    val date: String,
    val avgTempC: Float,
    val dailyChanceOfRain: Int,
    val conditionText: String,
    val conditionIcon: String
)
