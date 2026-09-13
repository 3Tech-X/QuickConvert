package com.three.tech.quickconvert.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AnimatedSplashScreen(
    onAnimationComplete: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    val animationState = rememberSplashAnimationState()
    val colors = rememberSplashColors()
    
    SplashAnimationTiming(onAnimationComplete)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        TechGridBackground(colors.primaryColor)
        HexagonalRings(animationState.rotation, colors)
        PulseEffect(animationState.pulseScale, animationState.pulseAlpha, colors.primaryColor)
        CurrencyIcon(animationState.scale, animationState.alpha, colors.primaryColor)
        AppBranding(animationState.alpha, colors.onBackgroundColor)
    }
}

@Composable
private fun rememberSplashAnimationState(): SplashAnimationState {
    val infiniteTransition = rememberInfiniteTransition(label = "splash_animation")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )
    
    return SplashAnimationState(rotation, scale, alpha, pulseScale, pulseAlpha)
}

@Composable
private fun rememberSplashColors(): SplashColors {
    return SplashColors(
        primaryColor = MaterialTheme.colorScheme.primary,
        secondaryColor = MaterialTheme.colorScheme.secondary,
        tertiaryColor = MaterialTheme.colorScheme.tertiary,
        onBackgroundColor = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun SplashAnimationTiming(onAnimationComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000L.milliseconds)
        onAnimationComplete()
    }
}

@Composable
private fun TechGridBackground(primaryColor: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridColor = primaryColor.copy(alpha = 0.1f)
        val gridSize = 50f
        val strokeWidth = 1f
        
        drawGridLines(gridColor, gridSize, strokeWidth)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawGridLines(
    gridColor: Color,
    gridSize: Float,
    strokeWidth: Float
) {
    for (i in 0 until (size.width.toInt() / gridSize.toInt())) {
        drawLine(
            color = gridColor,
            start = Offset(i * gridSize, 0f),
            end = Offset(i * gridSize, size.height),
            strokeWidth = strokeWidth
        )
    }
    for (i in 0 until (size.height.toInt() / gridSize.toInt())) {
        drawLine(
            color = gridColor,
            start = Offset(0f, i * gridSize),
            end = Offset(size.width, i * gridSize),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
private fun HexagonalRings(rotation: Float, colors: SplashColors) {
    OuterHexagonalRing(rotation, colors.primaryColor, colors.secondaryColor)
    InnerHexagonalRing(rotation, colors.tertiaryColor, colors.primaryColor)
}

@Composable
private fun OuterHexagonalRing(rotation: Float, primaryColor: Color, secondaryColor: Color) {
    Canvas(modifier = Modifier.size(200.dp)) {
        rotate(rotation) {
            drawHexagonPath(
                sides = 6,
                brush = Brush.linearGradient(colors = listOf(primaryColor, secondaryColor)),
                strokeWidth = 3f
            )
        }
    }
}

@Composable
private fun InnerHexagonalRing(rotation: Float, tertiaryColor: Color, primaryColor: Color) {
    Canvas(modifier = Modifier.size(150.dp)) {
        rotate(-rotation) {
            drawHexagonPath(
                sides = 6,
                brush = Brush.linearGradient(colors = listOf(tertiaryColor, primaryColor)),
                strokeWidth = 2f
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHexagonPath(
    sides: Int,
    brush: Brush,
    strokeWidth: Float
) {
    val path = Path()
    val radius = size.minDimension / 2 - 10f
    val centerX = size.width / 2
    val centerY = size.height / 2
    
    for (i in 0 until sides) {
        val angle = (i * 2 * Math.PI / sides)
        val x = centerX + radius * kotlin.math.cos(angle).toFloat()
        val y = centerY + radius * kotlin.math.sin(angle).toFloat()
        
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    
    drawPath(path = path, brush = brush, style = Stroke(width = strokeWidth))
}

@Composable
private fun PulseEffect(pulseScale: Float, pulseAlpha: Float, primaryColor: Color) {
    Canvas(modifier = Modifier.size(120.dp)) {
        drawCircle(
            color = primaryColor.copy(alpha = pulseAlpha),
            radius = (size.minDimension / 2) * pulseScale,
            style = Stroke(width = 2f)
        )
    }
}

@Composable
private fun CurrencyIcon(scale: Float, alpha: Float, primaryColor: Color) {
    Icon(
        imageVector = Icons.Default.CurrencyExchange,
        contentDescription = "App Icon",
        modifier = Modifier
            .size(80.dp)
            .scale(scale)
            .alpha(alpha),
        tint = primaryColor
    )
}

@Composable
private fun AppBranding(alpha: Float, onBackgroundColor: Color) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "QuickConvert",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = onBackgroundColor,
            modifier = Modifier
                .padding(bottom = 80.dp)
                .alpha(alpha)
        )
        Text(
            text = "Currency Converter",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = onBackgroundColor.copy(alpha = 0.7f),
            modifier = Modifier
                .padding(bottom = 50.dp)
                .alpha(alpha)
        )
    }
}

private data class SplashAnimationState(
    val rotation: Float,
    val scale: Float,
    val alpha: Float,
    val pulseScale: Float,
    val pulseAlpha: Float
)

private data class SplashColors(
    val primaryColor: Color,
    val secondaryColor: Color,
    val tertiaryColor: Color,
    val onBackgroundColor: Color
)
