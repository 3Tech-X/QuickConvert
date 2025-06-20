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

    fun getCalculatedCurrencyValue(
        baseCurrency: String,
        targetCurrency: String,
        amount: String
    ) {
        viewModelScope.launch {
            _networkResult.value = service.getCurrencyValue(
                baseCurrency,
                targetCurrency,
                amount.toFloat()
            )
        }
    }
}
