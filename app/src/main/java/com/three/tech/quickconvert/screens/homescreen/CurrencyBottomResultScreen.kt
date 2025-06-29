package com.three.tech.quickconvert.screens.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.networking.dataclass.Currency
import com.three.tech.quickconvert.networking.dataclass.NetworkError
import java.util.Locale

@Composable
fun ResultSuccessAndFailureView(response: Pair<Currency?, NetworkError?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        response.first?.let { currency ->
            ResultBottomSuccessScreen(currency)
        }

        response.second?.let { networkError ->
            ResultBottomErrorScreen(networkError)
        }
    }
}

@Composable
private fun ResultBottomSuccessScreen(currency: Currency) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.qc_result_image),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Quick Convert Logo"
                )
                Text(
                    modifier = Modifier
                        .padding(14.dp)
                        .align(Alignment.BottomStart),
                    text = String.format(
                        Locale.US,
                        "%.2f",
                        currency.conversionResult
                    ) + " " + currency.targetCode,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 14.dp, bottom = 14.dp),
                text = "Exchange Rate: " + "1 ${currency.baseCode} = ${currency.conversionRate} ${currency.targetCode}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ResultBottomErrorScreen(networkError: NetworkError) {
    val context = LocalContext.current
    val (text, description, image) = when (networkError) {
        NetworkError.NO_INTERNET -> Triple(
            context.getString(R.string.qc_error_title_no_internet),
            context.getString(R.string.qc_error_description_no_internet),
            R.drawable.qc_no_internet_image
        )
        else -> Triple(
            context.getString(R.string.qc_error_title),
            context.getString(R.string.qc_error_description),
            R.drawable.qc_server_error
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = image),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Quick Convert Error Logo"
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
