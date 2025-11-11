package io.github.msaggik.databackend.api.dto.enities

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDto(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayDto>
) : Parcelable
