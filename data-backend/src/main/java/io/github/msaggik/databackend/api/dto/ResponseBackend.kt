package io.github.msaggik.databackend.api.dto

open class ResponseBackend {
    var resultNetworkCode = DEFAULT

    companion object {
        const val DEFAULT = 0
        const val SUCCESS_OK = 200
        const val ERROR_NO_INTERNET = -1
        const val ERROR_UNKNOWN = -2
    }
}
