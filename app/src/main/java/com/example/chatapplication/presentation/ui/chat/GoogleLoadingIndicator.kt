package com.example.chatapplication.presentation.ui.chat

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GoogleLoadingIndicator(
    modifier: Modifier = Modifier,
    dotSize: Dp = 5.dp,
    dotColor: Color = Color.Gray,
    animationDelay: Int = 200
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val dotScaleValues = (0..2).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(index * animationDelay)
            ), label = ""
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dotScaleValues.forEachIndexed { index, scale ->
            Box(
                modifier = Modifier
                    .size(dotSize * scale.value)
                    .background(dotColor, CircleShape)
            )
            if (index < 2) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}