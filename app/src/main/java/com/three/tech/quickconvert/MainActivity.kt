package com.three.tech.quickconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.three.tech.quickconvert.navigation.QCNavigation
import com.three.tech.quickconvert.ui.theme.QuickConvertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {

        }
        enableEdgeToEdge()
        setContent {
            QuickConvertTheme {
                QCNavigation(this)
            }
        }
    }
}
