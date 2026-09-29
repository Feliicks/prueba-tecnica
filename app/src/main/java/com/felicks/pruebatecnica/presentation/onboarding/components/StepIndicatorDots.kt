package com.felicks.pruebatecnica.presentation.onboarding.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StepIndicatorDots(
    pageCount: Int,
    activePageIndex: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.outlineVariant,
    dotHeight: Dp = 8.dp,
    inactiveDotWidth: Dp = 8.dp,
    activeDotWidth: Dp = 24.dp,
    spacing: Dp = 6.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0 until pageCount) {
            val isActive = index == activePageIndex
            val dotWidth by animateDpAsState(
                targetValue = if (isActive) activeDotWidth else inactiveDotWidth,
                label = "dot_width_animation"
            )
            val color by animateColorAsState(
                targetValue = if (isActive) activeColor else inactiveColor,
                label = "dot_color_animation"
            )

            Box(
                modifier = Modifier
                    .height(dotHeight)
                    .width(dotWidth)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
