package com.three.tech.quickconvert.screens.homescreen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.networking.util.NetworkUtil
import com.three.tech.quickconvert.viewmodel.ConvertViewModel
import com.three.tech.quickconvert.viewmodel.QCHomePageData

@Composable
fun HandleAmountAndButton(
    currencyViewModel: ConvertViewModel,
    baseCurrency: String,
    qcHomePageData: QCAmountAndButtonData
) {
    val context = LocalContext.current
    var isAmountFieldFocused by remember { mutableStateOf(false) }
    qcHomePageData.apply {
        Column {
            TextField(
                value = inputFields.value.currencyValue,
                label = { Text("Amount to convert") },
                onValueChange = { currencyViewModel.onValueChange(it) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (!isInitialCompositionCompleted) return@onFocusChanged
                        isAmountFieldFocused = it.isFocused
                        if (!it.isFocused) {
                            currencyViewModel.validateInputField(inputFields.value.currencyValue)
                        }
                    },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                isError = !isAmountFieldFocused && inputFields.value.currencyValueError,
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            )

            if (inputFields.value.currencyValueError && !isAmountFieldFocused) {
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
                handleOnButtonClick(
                    focusManager,
                    currencyViewModel,
                    ButtonClickData(inputFields, context, baseCurrency, targetCurrency)
                )
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black
            )
        ) { HandleLoaderOnClick(isLoading.value) }
    }
}

private fun handleOnButtonClick(
    focusManager: FocusManager,
    currencyViewModel: ConvertViewModel,
    buttonClickData: ButtonClickData,
) {
    focusManager.clearFocus()
    buttonClickData.apply {
        currencyViewModel.handleButtonClick(
            NetworkUtil.checkForInternet(context),
            baseCurrency,
            targetCurrency
        )
    }
}

data class ButtonClickData(
    val inputFields: State<QCHomePageData>,
    val context: Context,
    val baseCurrency: String,
    val targetCurrency: String
)

data class QCAmountAndButtonData(
    val targetCurrency: String,
    val isLoading: State<Boolean>,
    val focusManager: FocusManager,
    val inputFields: State<QCHomePageData>,
    val isInitialCompositionCompleted: Boolean
)
