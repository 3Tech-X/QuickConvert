package com.three.tech.quickconvert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.three.tech.quickconvert.ConverterService
import com.three.tech.quickconvert.networking.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val service: ConverterService
) : ViewModel() {

    private var _networkResult = MutableStateFlow<NetworkResult?>(null)
    val networkResult: StateFlow<NetworkResult?>
        get() = _networkResult.asStateFlow()

    private var _uiState = MutableStateFlow(QCHomePageData())
    val uiState: StateFlow<QCHomePageData>
        get() = _uiState.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    fun getCalculatedCurrencyValue(
        baseCurrency: String,
        targetCurrency: String,
        amount: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _networkResult.value = service.getCurrencyValue(
                baseCurrency,
                targetCurrency,
                amount.toFloat()
            )
            _isLoading.value = false
        }
    }

    fun onValueChange(amount: String) {
        _uiState.value = _uiState.value.copy(currencyValue = amount)
    }

    fun validateInputField(amount: String) {
        if (amount.isEmpty()) {
            _uiState.value = _uiState.value.copy(currencyValueError = true)
        } else {
            _uiState.value = _uiState.value.copy(currencyValueError = false)

        }
    }

    fun handleButtonClick(checkForInternet: Boolean, baseCurrency: String, targetCurrency: String) {
        validateInputField(_uiState.value.currencyValue)
        if (!_uiState.value.currencyValueError && checkForInternet) {
            getCalculatedCurrencyValue(
                baseCurrency = baseCurrency,
                targetCurrency = targetCurrency,
                amount = _uiState.value.currencyValue
            )
        }

    }
}

data class QCHomePageData(
    val currencyValue: String = "",
    val currencyValueError: Boolean = false,
)
