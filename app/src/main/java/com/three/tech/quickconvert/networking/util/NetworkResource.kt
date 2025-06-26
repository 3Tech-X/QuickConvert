package com.three.tech.quickconvert.networking.util

import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError

sealed class NetworkResult(val error: NetworkError? = null, val data: Currency? = null) {

    class Success(data: Currency, error: NetworkError? = null): NetworkResult(data = data, error = error)

    class Error(error: NetworkError): NetworkResult(error)
}

