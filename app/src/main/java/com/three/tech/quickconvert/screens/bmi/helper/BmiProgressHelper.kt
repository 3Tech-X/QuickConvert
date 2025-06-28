package com.three.tech.quickconvert.screens.bmi.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BMIProgressBar(bmi: Double, modifier: Modifier = Modifier) {
    val maxBMI = 50.0
    val progress = (bmi / maxBMI).coerceIn(0.0, 1.0).toFloat()

    val color = when {
        bmi < 18.5 -> Color(0xFF64B5F6) // Blue - Underweight
        bmi < 25.0 -> Color(0xFF4CAF50) // Green - Normal
        bmi < 30.0 -> Color(0xFFFFC107) // Yellow - Overweight
        bmi < 35.0 -> Color(0xFFFF9800) // Orange - Obese I
        else -> Color(0xFFF44336)       // Red - Obese II or higher
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(color)
        )
    }
}
