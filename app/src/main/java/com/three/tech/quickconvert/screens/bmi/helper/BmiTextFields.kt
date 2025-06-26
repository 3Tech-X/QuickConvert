package com.three.tech.quickconvert.screens.bmi.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.viewmodel.BMIPageData
import com.three.tech.quickconvert.viewmodel.BodyMassViewModel

@Composable
fun BMITextFields(
    isInitialCompositionCompleted: Boolean,
    bmiViewModel: BodyMassViewModel,
    focusManager: FocusManager,
    inputFields: State<BMIPageData>
) {
    Column {
        TextField(
            value = inputFields.value.heightValue,
            label = { Text("Height (cm)") },
            onValueChange = { bmiViewModel.onValueChange(it, null) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (!isInitialCompositionCompleted) return@onFocusChanged
                    if (!it.isFocused) {
                        bmiViewModel.validateInputField(
                            heightValue = inputFields.value.heightValue,
                            weightValue = inputFields.value.weightValue
                        )
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            isError = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
        )

        if (false) {
            Text(
                text = "Please enter a valid amount",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
    WeightTextField(inputFields, bmiViewModel, isInitialCompositionCompleted, focusManager)
}

@Composable
private fun WeightTextField(
    inputFields: State<BMIPageData>,
    bmiViewModel: BodyMassViewModel,
    isInitialCompositionCompleted: Boolean,
    focusManager: FocusManager
) {
    Column {
        TextField(
            value = inputFields.value.weightValue,
            label = { Text("Weight (kg)") },
            onValueChange = {
                bmiViewModel.onValueChange(null, it)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (!isInitialCompositionCompleted) return@onFocusChanged
                    if (!it.isFocused) {
                        bmiViewModel.validateInputField(
                            heightValue = inputFields.value.heightValue,
                            weightValue = inputFields.value.weightValue
                        )
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            isError = false,
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
        )

        if (false) {
            Text(
                text = "Please enter a valid amount",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
