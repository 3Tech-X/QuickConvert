package com.three.tech.quickconvert.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.networking.util.NetworkUtil
import com.three.tech.quickconvert.viewmodel.ConvertViewModel

@Composable
fun HandleAmountAndButton(
    currencyViewModel: ConvertViewModel,
    baseCurrency: String,
    targetCurrency: String,
    isLoading: State<Boolean>
) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var noInternetMessage by remember { mutableStateOf<String?>(null) }
    Column {
        TextField(
            value = amount,
            label = { Text("Amount to convert") },
            onValueChange = { amount = it },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            isError = amount.isEmpty() || amount.toDoubleOrNull() == null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        if (amount.isEmpty() || amount.toDoubleOrNull() == null) {
            Text(
                text = "Please enter a valid amount",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }


    Button(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            if (NetworkUtil.checkForInternet(context)) {
                currencyViewModel.getCalculatedCurrencyValue(
                    baseCurrency = baseCurrency,
                    targetCurrency = targetCurrency,
                    amount = amount
                )
            } else {
                noInternetMessage = "No Internet Connection is available."
            }
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black
        )
    ) { HandleLoaderOnClick(isLoading.value) }
}
