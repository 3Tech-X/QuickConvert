package com.three.tech.quickconvert.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BodyMassViewModel @Inject constructor() : ViewModel() {
    private var _uiState = MutableStateFlow(BMIPageData())
    val uiState: StateFlow<BMIPageData>
        get() = _uiState.asStateFlow()

    fun onValueChange(heightValue: String?, weightValue: String?) {
        heightValue?.let {
            _uiState.value = _uiState.value.copy(heightValue = heightValue)

        }

        weightValue?.let {
            _uiState.value = _uiState.value.copy(weightValue = weightValue)

        }
    }

    fun validateHeightValue(heightValue: String) {
        if (heightValue.isBlank() || heightValue.toFloatOrNull() == null) {
            _uiState.value = _uiState.value.copy(heightValueError = true)
        } else {
            _uiState.value = _uiState.value.copy(heightValueError = false)
        }
    }


    fun validateWeightValue(weightValue: String) {
        if (weightValue.isBlank() || weightValue.toFloatOrNull() == null) {
            _uiState.value = _uiState.value.copy(weightValueError = true)
        } else {
            _uiState.value = _uiState.value.copy(weightValueError = false)
        }
    }

    fun calculateBmi(heightValue: String, weightValue: String) {
        validateHeightValue(heightValue)
        validateWeightValue(weightValue)
        if (!uiState.value.heightValueError && !uiState.value.weightValueError) {
            val heightM = heightValue.toFloat() / 100
            val bmi = (weightValue.toFloat() / (heightM * heightM))
            _uiState.value = _uiState.value.copy(typeOfBmi = getBMICategory(bmi.toDouble()))
            _uiState.value = _uiState.value.copy(bmiValue = String.format(Locale.US,"%.2f", bmi))
        }
    }

    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal weight"
            bmi < 30.0 -> "Overweight"
            bmi < 35.0 -> "Obesity Class I"
            bmi < 40.0 -> "Obesity Class II"
            else -> "Obesity Class III"
        }
    }
}


data class BMIPageData(
    val heightValue: String = "",
    val weightValue: String = "",
    var bmiValue: String = "",
    var typeOfBmi: String = "",
    val heightValueError: Boolean = false,
    val weightValueError: Boolean = false,
)