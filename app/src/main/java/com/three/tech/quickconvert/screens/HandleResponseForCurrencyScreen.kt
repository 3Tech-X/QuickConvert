package com.three.tech.quickconvert.screens

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.networking.util.NetworkResult

@Composable
fun qCResponse(
    response: State<NetworkResult?>,
    currency: Currency?,
    noInternetMessage: String?,
    errorMessage: NetworkError?,
    isLoading: (Boolean) -> Unit,
): Triple<Currency?, NetworkError?, String?> {
    var currency1 = currency
    var noInternetMessage1 = noInternetMessage
    var errorMessage1 = errorMessage
    response.value?.let { apiResponse ->
        isLoading(false)
        when (apiResponse) {
            is NetworkResult.Success -> {
                currency1 = apiResponse.data
                noInternetMessage1 = null
            }

            is NetworkResult.Error -> {
                errorMessage1 = apiResponse.error
            }
        }
    }
    return Triple(currency1, errorMessage1, noInternetMessage1)
}

@Composable
fun HandleLoaderOnClick(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(15.dp),
            strokeWidth = 1.dp,
            color = Color.Red
        )
    } else {
        Text("Convert")
    }
}