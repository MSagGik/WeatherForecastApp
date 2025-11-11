package io.github.msaggik.weatherforecastapp.domain.models

import io.github.msaggik.weatherforecastapp.domain.models.entities.ForecastDay
import io.github.msaggik.weatherforecastapp.domain.models.entities.Location

data class Forecast(
    val location: Location,
    val forecastDayList: List<ForecastDay>
)
