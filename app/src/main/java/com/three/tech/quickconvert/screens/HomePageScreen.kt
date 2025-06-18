package com.three.tech.quickconvert.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.ConverterService
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.networking.util.NetworkResult
import com.three.tech.quickconvert.networking.util.NetworkUtil
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QCHomePage(service: ConverterService, onClose: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var amount by remember { mutableStateOf("100") }
    var baseCurrency by remember { mutableStateOf("USD") }
    var targetCurrency by remember { mutableStateOf("INR") }
    var currency by remember { mutableStateOf<Currency?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<NetworkError?>(null) }
    var noInternetMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White), topBar = {
        androidx.compose.material3.CenterAlignedTopAppBar(
            modifier = Modifier.background(Color.White),
            title = {
                Text(
                    text = context.getString(R.string.qc_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onClose()
                        }
                        .size(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Quick Convert Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.qc_home_image),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = "Quick Convert Logo"
                        )
                        Text(
                            text = "Currency Conversion",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = "Enter details below",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }



                TextField(
                    value = baseCurrency,
                    onValueChange = { baseCurrency = it },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    placeholder = { Text("Base Currency") }
                )

                TextField(
                    value = targetCurrency,
                    onValueChange = { targetCurrency = it },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    placeholder = { Text("Target Currency") }
                )

                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("Amount") }
                )

                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (NetworkUtil.checkForInternet(context)) {
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
                                        noInternetMessage = null
                                    }

                                    is NetworkResult.Error -> {
                                        errorMessage = response.error
                                    }
                                }
                                isLoading = false
                            }
                        } else {
                            noInternetMessage = "No Internet Connection is available."
                        }
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black
                    )
                ) {
                    HandleLoaderOnClick(isLoading)
                }

                noInternetMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red
                    )
                }

                errorMessage?.let {
                    Text(
                        text = it.toString(),
                        color = Color.Red
                    )
                }

                currency?.let {
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

@Composable
private fun HandleLoaderOnClick(isLoading: Boolean) {
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