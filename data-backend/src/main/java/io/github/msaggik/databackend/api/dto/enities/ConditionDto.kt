package io.github.msaggik.databackend.api.dto.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ConditionDto(
    val text: String,
    val icon: String
) : Parcelable
