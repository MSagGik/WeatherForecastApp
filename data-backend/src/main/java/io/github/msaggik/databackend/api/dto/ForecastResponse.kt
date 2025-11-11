package io.github.msaggik.databackend.api.dto

import io.github.msaggik.databackend.api.dto.enities.ForecastDto
import io.github.msaggik.databackend.api.dto.enities.LocationDto
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ForecastResponse(
    val location: LocationDto,
    val forecast: ForecastDto
) : ResponseBackend(), Parcelable
