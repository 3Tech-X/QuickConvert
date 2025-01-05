package com.three.tech.quickconvert.networking

import com.three.tech.quickconvert.ConverterService
import com.three.tech.quickconvert.networking.util.NetworkResult
import javax.inject.Inject

class ConverterServiceImpl @Inject constructor (
    private val currencyConverter: CurrencyConverterClient
): ConverterService {

    override suspend fun getCurrencyValue(baseCurrency: String, targetCurrency: String, amount: Int): NetworkResult {
        return currencyConverter.convertCurrency(baseCurrency, targetCurrency, amount)
    }

    override suspend fun doSomething() {
        // Do something
    }
}