package com.three.tech.quickconvert.screens.homescreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError

@Composable
fun ResultText(response: Triple<Currency?, NetworkError?, String?>) {
    response.first?.let { currency ->
        Text(
            text = currency.conversionRate.toString(),
        )
        Text(
            text = currency.conversionResult.toString(),
        )
        Text(
            text = currency.baseCode,
        )
        Text(
            text = currency.targetCode,
        )
    }

    response.second?.let {
        Text(
            text = it.toString(),
            color = Color.Red
        )
    }

    response.third?.let {
        Text(
            text = it,
            color = Color.Red
        )
    }
}