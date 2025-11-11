package io.github.msaggik.weatherforecastapp.utils

sealed interface NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>
    object Loading : NetworkState<Nothing>
    object  InternetOff : NetworkState<Nothing>
    object  InternetError : NetworkState<Nothing>
}
