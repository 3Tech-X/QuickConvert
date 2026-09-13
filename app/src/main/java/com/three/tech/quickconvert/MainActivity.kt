package com.three.tech.quickconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.three.tech.quickconvert.navigation.QCNavigation
import com.three.tech.quickconvert.ui.AnimatedSplashScreen
import com.three.tech.quickconvert.ui.theme.QuickConvertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        
        enableEdgeToEdge()
        setContent {
            var showSplashScreen by remember { mutableStateOf(true) }
            QuickConvertTheme {
                if (showSplashScreen) {
                    AnimatedSplashScreen(
                        onAnimationComplete = {
                            showSplashScreen = false
                        }
                    )
                } else {
                    QCNavigation(this)
                }
            }
        }
    }
}
