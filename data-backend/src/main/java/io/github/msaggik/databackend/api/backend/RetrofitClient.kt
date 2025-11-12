package io.github.msaggik.databackend.api.backend

import androidx.annotation.RequiresPermission
import io.github.msaggik.databackend.api.WeatherApiClient
import io.github.msaggik.databackend.api.dto.ResponseBackend
import retrofit2.HttpException
import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

internal class RetrofitClient(
    private val weatherApiService: WeatherApiService,
    private val context: Context
) : WeatherApiClient {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun doRequestGetWeather(
        location: String,
        days: Int
    ): ResponseBackend {
        return if (isConnected()) {
            runCatching {
                weatherApiService.getForecast(
                    location = location,
                    days = days
                )
            }.fold(
                onSuccess = { it.apply { resultNetworkCode = ResponseBackend.SUCCESS_OK } },
                onFailure = { exception ->
                    ResponseBackend().apply {
                        resultNetworkCode = if (exception is HttpException) {
                            exception.code()
                        } else {
                            ResponseBackend.ERROR_UNKNOWN
                        }
                    }
                }
            )
        } else {
            ResponseBackend().apply {
                resultNetworkCode = ResponseBackend.ERROR_NO_INTERNET
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
