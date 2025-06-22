package com.three.tech.quickconvert.screens

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.networking.util.NetworkResult

@Composable
fun qCResponse(
    response: State<NetworkResult?>,
    errorMessage: NetworkError?,
): Triple<Currency?, NetworkError?, String?> {
    var currency by remember { mutableStateOf<Currency?>(null) }
    var noInternetMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage1 = errorMessage
    response.value?.let { apiResponse ->
        when (apiResponse) {
            is NetworkResult.Success -> {
                currency = apiResponse.data
                noInternetMessage = null
            }

            is NetworkResult.Error -> {
                errorMessage1 = apiResponse.error
            }
        }
    }
    return Triple(currency, errorMessage1, noInternetMessage)
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
        Text(
            text = "Convert",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}