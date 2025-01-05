package com.three.tech.quickconvert.networking

import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.networking.util.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class CurrencyConverterClient(
    private val httpClient: HttpClient
) {
    suspend fun convertCurrency(
        baseCurrency: String,
        targetCurrency: String,
        amount: Int
    ): NetworkResult {
        val response = try {
            httpClient.get(
                urlString = "https://v6.exchangerate-api.com/v6/026f9f8e2e8e2e310cd6f6d0/pair/${baseCurrency}/${targetCurrency}/${amount}"
            )
        } catch (e: UnresolvedAddressException) {
            return NetworkResult.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return NetworkResult.Error(NetworkError.SOMETHING_WENT_WRONG)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val currency = response.body<Currency>()
                NetworkResult.Success(currency)
            }

            in 500..599 -> NetworkResult.Error(NetworkError.SERVER_ERROR)
            else -> NetworkResult.Error(NetworkError.SOMETHING_WENT_WRONG)
        }

    }

}
