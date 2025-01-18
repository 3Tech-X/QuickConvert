package com.three.tech.quickconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.networking.util.NetworkResult
import com.three.tech.quickconvert.ui.theme.QuickConvertTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var service: ConverterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickConvertTheme {
                val scope = rememberCoroutineScope()
                var amount by remember {
                    mutableStateOf("100")
                }
                var baseCurrency by remember {
                    mutableStateOf("USD")
                }

                var targetCurrency by remember {
                    mutableStateOf("INR")
                }

                var currency by remember {
                    mutableStateOf<Currency?>(null)
                }
                var isLoading by remember {
                    mutableStateOf(false)
                }
                var errorMessage by remember {
                    mutableStateOf<NetworkError?>(null)
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        TextField(
                            value = baseCurrency,
                            onValueChange = { baseCurrency = it },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text("Base Currency")
                            }
                        )

                        TextField(
                            value = targetCurrency,
                            onValueChange = { targetCurrency = it },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text("Target Currency")
                            }
                        )
                        TextField(
                            value = amount,
                            onValueChange = { amount = it },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text("Base Currency")
                            }
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    isLoading = true
                                    val response = service.getCurrencyValue(
                                        baseCurrency,
                                        targetCurrency,
                                        amount.toFloat()
                                    )
                                    when (response) {
                                        is NetworkResult.Success -> {
                                            currency = response.data
                                        }

                                        is NetworkResult.Error -> {
                                            errorMessage = response.error
                                        }
                                    }
                                    isLoading = false
                                }
                            },
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                            ),
                        ) {
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

                        errorMessage?.let {
                            Text(
                                text = it.toString(),
                                color = Color.Red
                            )
                        }
                        Text(
                            text = currency?.conversionRate.toString(),
                        )
                        Text(
                            text = currency?.conversionResult.toString(),
                        )
                        Text(
                            text = currency?.baseCode.toString(),
                        )
                        Text(
                            text = currency?.targetCode.toString(),
                        )
                    }
                }
            }
        }
    }
}
