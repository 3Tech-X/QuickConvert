package com.three.tech.quickconvert

import com.three.tech.quickconvert.networking.util.NetworkResult

interface ConverterService {

    suspend fun getCurrencyValue(baseCurrency: String, targetCurrency: String, amount: Float): NetworkResult

    suspend fun doSomething()

}
