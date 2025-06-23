package com.three.tech.quickconvert.screens.homescreen

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import com.three.tech.quickconvert.screens.navigationbar.CustomNavigationBar
import com.three.tech.quickconvert.viewmodel.ConvertViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QCHomePage(onClose: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val currencyViewModel = hiltViewModel<ConvertViewModel>()
    var baseCurrency by remember { mutableStateOf("") }
    var targetCurrency by remember { mutableStateOf("") }
    val errorMessage by remember { mutableStateOf<NetworkError?>(null) }

    val networkResult = currencyViewModel.networkResult.collectAsState()
    val inputFields = currencyViewModel.uiState.collectAsState()
    var isInitialCompositionCompleted by remember { mutableStateOf(false) }
    val isLoading = currencyViewModel.isLoading.collectAsState()
    val response = qCResponse(networkResult, errorMessage)
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), topBar = {

            androidx.compose.material3.CenterAlignedTopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
        },

        bottomBar = {
            CustomNavigationBar(context)
        }

    ) { innerPadding ->
        LaunchedEffect(Unit) { isInitialCompositionCompleted = true }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .clickable {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
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
                SearchableDropdown(
                    items = listOf(
                        "USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK",
                        "NZD", "MXN", "SGD", "HKD", "NOK", "KRW"
                    ),
                    label = "Base Currency",
                    onItemSelected = { selectedItem ->
                        baseCurrency = selectedItem
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                )

                SearchableDropdown(
                    items = listOf(
                        "USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK",
                        "NZD", "MXN", "SGD", "HKD", "NOK", "KRW"
                    ),
                    label = "Convert to Currency",
                    onItemSelected = { selectedItem ->
                        targetCurrency = selectedItem
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                )

                HandleAmountAndButton(
                    currencyViewModel, baseCurrency,
                    QCAmountAndButtonData(
                        targetCurrency,
                        isLoading,
                        focusManager,
                        inputFields,
                        isInitialCompositionCompleted
                    )
                )

                ResultText(response)

            }
        }
    }
}
