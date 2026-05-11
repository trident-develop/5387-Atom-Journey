package com.vfg.silkroad.goo.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan

@Composable
fun GlowingCard(
    modifier: Modifier = Modifier,
    glow: Color = ElectricCyan,
    cornerRadius: Dp = 22.dp,
    contentPadding: Dp = 18.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val infinite = rememberInfiniteTransition(label = "glowingCard")
    val pulse by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val shape = RoundedCornerShape(cornerRadius)
    val base = modifier
        .shadow(
            elevation = 18.dp,
            shape = shape,
            ambientColor = glow.copy(alpha = pulse),
            spotColor = glow.copy(alpha = pulse)
        )
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(DeepBlueCard, DeepBlueElevated),
                start = Offset.Zero,
                end = Offset.Infinite
            )
        )
        .border(
            width = 1.4.dp,
            brush = Brush.linearGradient(
                listOf(glow.copy(alpha = 0.85f), glow.copy(alpha = 0.15f))
            ),
            shape = shape
        )
    val clickable = if (onClick != null) base.clickable { onClick() } else base
    Box(modifier = clickable.padding(contentPadding)) {
        Column(content = content)
    }
}
