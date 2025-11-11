package io.github.msaggik.weatherforecastapp.data.mappers

import io.github.msaggik.databackend.api.dto.ForecastResponse
import io.github.msaggik.databackend.api.dto.enities.ForecastDto
import io.github.msaggik.databackend.api.dto.enities.LocationDto
import io.github.msaggik.weatherforecastapp.domain.models.Forecast
import io.github.msaggik.weatherforecastapp.domain.models.entities.ForecastDay
import io.github.msaggik.weatherforecastapp.domain.models.entities.Location

class WeatherMapper {
    fun map(forecast: ForecastResponse): Forecast {
        return Forecast(
            location = map(forecast.location),
            forecastDayList = map(forecast.forecast)
        )
    }

    private fun map(location: LocationDto): Location {
        return Location(
            name = location.name,
            country = location.country,
            localTime = location.localTime
        )
    }

    private fun map(forecast: ForecastDto): List<ForecastDay> {
        return forecast.forecastDay.map {
            with(it) {
                ForecastDay(
                    date = date,
                    avgTempC = day.avgTempC,
                    dailyChanceOfRain = day.dailyChanceOfRain,
                    conditionText = day.condition.text,
                    conditionIcon = day.condition.icon
                )
            }
        }
    }
}
