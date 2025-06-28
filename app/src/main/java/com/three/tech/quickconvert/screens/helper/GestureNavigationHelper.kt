package com.three.tech.quickconvert.screens.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun isGestureNavigation(): Boolean {
    val view = LocalView.current
    val density = LocalDensity.current

    return remember {
        val insets = ViewCompat.getRootWindowInsets(view)
        val navBarHeightPx = insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0

        // Convert threshold (e.g., 24dp) to pixels for comparison
        val thresholdPx = with(density) { 24.dp.toPx().toInt() }

        navBarHeightPx > thresholdPx // Likely gesture nav if small
    }
}
