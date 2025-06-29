package com.three.tech.quickconvert.screens.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.navigation.NavigationType
import com.three.tech.quickconvert.screens.helper.CardView
import com.three.tech.quickconvert.screens.navigationbar.CustomNavigationBar
import com.three.tech.quickconvert.util.openAsCustomTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBackPress: () -> Unit, onNavBarClickedClicked: (NavigationType) -> Unit) {
    BackHandler {
        onBackPress()
    }
    val context = LocalContext.current
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
                                onBackPress()
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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier,
                    text = "Package name: com.three.tech.quickconvert",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier,
                    text = "Version: v1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                CustomNavigationBar(2) {
                    onNavBarClickedClicked(it)
                }

            }
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                AndroidCard()
                FeedbackCard()
                PrivacyPolicyCard()
            }
        }

    }
}

@Composable
fun FeedbackCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    CardView {
        Text(
            text = context.getString(R.string.qc_about_feedback_discussion),
            modifier = modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium,
        )
        Button(
            onClick = { "https://t.me/qc_chat".openAsCustomTab(context) },
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Language,
                modifier = modifier
                    .padding(8.dp)
                    .size(18.dp),
                contentDescription = "Telegram",
            )
            Text(text = "Telegram")
        }
    }
}

@Composable
fun PrivacyPolicyCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    CardView {
        Text(
            text = context.getString(R.string.qc_about_privacy_policy),
            modifier = modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium,
        )
        Button(
            onClick = {
                "https://sites.google.com/view/quickconvertprivacypolicy/home".openAsCustomTab(
                    context
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.PrivacyTip,
                modifier = modifier
                    .padding(8.dp)
                    .size(18.dp),
                contentDescription = "Privacy Policy",
            )
            Text(text = context.getString(R.string.qc_about_privacy_policy_button))
        }
    }
}

@Composable
fun AndroidCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    CardView {
        Text(
            text = "Android",
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = { "https://github.com/Shreyas280598".openAsCustomTab(context) },
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.convert_bottom_icon),
                    modifier = modifier
                        .padding(12.dp)
                        .size(18.dp),
                    contentDescription = "Test",
                )
                Text(text = "shreyas_sathya")
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = { "https://github.com/3Tech-X/QuickConvert".openAsCustomTab(context) },
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Code,
                    modifier = modifier
                        .padding(12.dp)
                        .size(18.dp),
                    contentDescription = "GitHub",
                )
                Text(
                    text = "Source Code",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}
