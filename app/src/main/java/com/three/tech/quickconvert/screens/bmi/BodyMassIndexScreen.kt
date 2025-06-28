package com.three.tech.quickconvert.screens.bmi

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.three.tech.quickconvert.navigation.NavigationType
import com.three.tech.quickconvert.screens.bmi.helper.BMIProgressBar
import com.three.tech.quickconvert.screens.bmi.helper.BMITextFields
import com.three.tech.quickconvert.screens.navigationbar.CustomNavigationBar
import com.three.tech.quickconvert.viewmodel.BodyMassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMICalculatorScreen(onBackPress: () -> Unit, onNavBarClickedClicked: (NavigationType) -> Unit) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val bodyMassViewModel = hiltViewModel<BodyMassViewModel>()
    val inputFields = bodyMassViewModel.uiState.collectAsState()
    var isInitialCompositionCompleted by remember { mutableStateOf(false) }

    BackHandler {
        onBackPress()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                title = {
                    Text(
                        text = context.getString(R.string.qc_main_title_bmi),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onBackPress()
                            }
                            .size(24.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back_arrow),
                            contentDescription = "Quick Convert Logo",
                            modifier = Modifier
                                .fillMaxSize(),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            )
        },

        bottomBar = {
            CustomNavigationBar(1) {
                onNavBarClickedClicked(it)
            }
        }

    ) { innerPadding ->
        LaunchedEffect(Unit) { isInitialCompositionCompleted = true }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { focusManager.clearFocus() }
                )
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
                            painter = painterResource(id = R.drawable.qc_bmi_image),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = "Quick Convert Logo"
                        )
                        Text(
                            text = context.getString(R.string.qc_title_bmi),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = context.getString(R.string.qc_desc_bmi),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                BMITextFields(
                    isInitialCompositionCompleted,
                    bodyMassViewModel,
                    focusManager,
                    inputFields
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        focusManager.clearFocus()
                        bodyMassViewModel.calculateBmi(
                            heightValue = inputFields.value.heightValue,
                            weightValue = inputFields.value.weightValue
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = context.getString(R.string.qc_button_bmi),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                if (inputFields.value.bmiValue.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                style = MaterialTheme.typography.titleLarge,
                                text = "Result"
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(36.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 20.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    text = inputFields.value.bmiValue
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(top = 20.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    text = inputFields.value.typeOfBmi
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                text = "BMI Range"
                            )
                            BMIProgressBar(
                                bmi = inputFields.value.bmiValue.toDoubleOrNull() ?: 0.0,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
