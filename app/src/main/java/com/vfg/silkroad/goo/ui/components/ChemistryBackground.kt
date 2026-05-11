package com.vfg.silkroad.goo.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlue
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonPink
import com.vfg.silkroad.goo.ui.theme.NeonViolet
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    val xSeed: Float,
    val ySeed: Float,
    val speed: Float,
    val radius: Float,
    val phase: Float,
    val color: Color,
    val drift: Float
)

@Composable
fun ChemistryBackground(modifier: Modifier = Modifier, intensity: Float = 1f) {
    val particles = remember {
        val palette = listOf(ElectricCyan, AcidGreen, NeonViolet, NeonPink)
        List(34) {
            Particle(
                xSeed = Random.nextFloat(),
                ySeed = Random.nextFloat(),
                speed = 0.25f + Random.nextFloat() * 0.55f,
                radius = 1.2f + Random.nextFloat() * 3.2f,
                phase = Random.nextFloat(),
                color = palette[Random.nextInt(palette.size)],
                drift = (Random.nextFloat() - 0.5f) * 0.18f
            )
        }
    }
    val infinite = rememberInfiniteTransition(label = "bg")
    val t by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(14000, easing = LinearEasing)),
        label = "t"
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(DeepBlueElevated, DeepBlue),
                center = Offset(size.width * 0.3f, size.height * 0.25f),
                radius = size.maxDimension * 0.95f
            )
        )
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(NeonViolet.copy(alpha = 0.10f), Color.Transparent),
                center = Offset(size.width * 0.85f, size.height * 0.85f),
                radius = size.minDimension * 0.7f
            )
        )
        particles.forEach { p ->
            val phase = (t + p.phase) % 1f
            val yNorm = ((p.ySeed + phase * p.speed) % 1f + 1f) % 1f
            val xNorm = ((p.xSeed + p.drift * sin(phase * 2f * PI.toFloat())) % 1f + 1f) % 1f
            val y = size.height * yNorm
            val x = size.width * xNorm
            val alpha = (0.35f + 0.45f * sin(phase * 2f * PI.toFloat())) * intensity
            drawCircle(
                color = p.color.copy(alpha = alpha.coerceIn(0f, 0.55f)),
                radius = p.radius * density,
                center = Offset(x, y)
            )
            drawCircle(
                color = p.color.copy(alpha = (alpha * 0.25f).coerceAtLeast(0f)),
                radius = p.radius * density * 2.4f,
                center = Offset(x, y)
            )
        }
    }
}
