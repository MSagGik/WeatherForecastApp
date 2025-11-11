package io.github.msaggik.databackend.api.dto.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDayDto(
    val date: String,
    val day: DayDto
) : Parcelable
