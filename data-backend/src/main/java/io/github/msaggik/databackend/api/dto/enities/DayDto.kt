package io.github.msaggik.databackend.api.dto.enities

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayDto(
    @SerializedName("avgtemp_c")
    val avgTempC: Float,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    val condition: ConditionDto
) : Parcelable
