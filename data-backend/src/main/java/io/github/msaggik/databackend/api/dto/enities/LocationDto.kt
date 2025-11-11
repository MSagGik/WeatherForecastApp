package io.github.msaggik.databackend.api.dto.enities

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class LocationDto(
    val name: String,
    val country: String,
    @SerializedName("localtime")
    val localTime: String
) : Parcelable
