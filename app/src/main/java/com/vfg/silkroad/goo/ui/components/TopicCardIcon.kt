package com.vfg.silkroad.goo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.Visual
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.NeonPink
import com.vfg.silkroad.goo.ui.theme.NeonRed
import com.vfg.silkroad.goo.ui.theme.NeonViolet
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun TopicCardIcon(
    visual: Visual,
    accent: Color,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(accent.copy(alpha = 0.30f), Color.Transparent)
                )
            )
    ) {
        when (visual) {
            Visual.Atom, Visual.Shells -> AtomVisual(
                modifier = Modifier.fillMaxSize().padding(6.dp),
                nucleusColor = NeonAmber,
                orbitColor = accent,
                electronColor = AcidGreen,
                orbits = 2
            )

            Visual.Molecule, Visual.DoubleBond -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val cy = this.size.height / 2f
                val left = Offset(this.size.width * 0.25f, cy)
                val right = Offset(this.size.width * 0.75f, cy)
                drawLine(accent, left, right, 5f * density, cap = StrokeCap.Round)
                drawCircle(accent, this.size.minDimension * 0.18f, left)
                drawCircle(NeonPink, this.size.minDimension * 0.18f, right)
            }

            Visual.PhScale -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val grad = Brush.horizontalGradient(
                    listOf(NeonRed, NeonAmber, AcidGreen, accent, NeonViolet)
                )
                drawRoundRect(
                    brush = grad,
                    topLeft = Offset(0f, this.size.height * 0.35f),
                    size = Size(this.size.width, this.size.height * 0.30f),
                    cornerRadius = CornerRadius(20f, 20f)
                )
            }

            Visual.Reaction -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val cy = this.size.height / 2f
                drawCircle(AcidGreen, 7f * density, Offset(this.size.width * 0.18f, cy))
                drawLine(accent, Offset(this.size.width * 0.30f, cy), Offset(this.size.width * 0.72f, cy), 4f * density, cap = StrokeCap.Round)
                drawLine(accent, Offset(this.size.width * 0.72f, cy), Offset(this.size.width * 0.62f, cy - 8f), 4f * density, cap = StrokeCap.Round)
                drawLine(accent, Offset(this.size.width * 0.72f, cy), Offset(this.size.width * 0.62f, cy + 8f), 4f * density, cap = StrokeCap.Round)
                drawCircle(NeonPink, 9f * density, Offset(this.size.width * 0.85f, cy))
            }

            Visual.PeriodicCell -> {
                Box(Modifier.fillMaxSize().padding(8.dp), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                            .background(accent.copy(alpha = 0.18f))
                            .background(
                                Brush.verticalGradient(
                                    listOf(accent.copy(alpha = 0.25f), Color.Transparent)
                                )
                            )
                    ) {
                        androidx.compose.foundation.layout.Column(
                            Modifier.fillMaxSize().padding(6.dp),
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                        ) {
                            androidx.compose.material3.Text(
                                "6",
                                color = accent,
                                style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                            )
                            androidx.compose.material3.Text(
                                "C",
                                color = accent,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }

            Visual.EnergyExo, Visual.EnergyEndo -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val w = this.size.width
                val h = this.size.height
                val baseY = if (visual == Visual.EnergyExo) h * 0.35f else h * 0.65f
                val endY = if (visual == Visual.EnergyExo) h * 0.65f else h * 0.30f
                val steps = 32
                val path = androidx.compose.ui.graphics.Path()
                path.moveTo(0f, baseY)
                for (i in 0..steps) {
                    val tt = i / steps.toFloat()
                    val x = w * tt
                    val y = baseY + (endY - baseY) * tt - 18f * sin(tt * PI.toFloat())
                    path.lineTo(x, y)
                }
                drawPath(path, color = accent, style = Stroke(width = 3f * density, cap = StrokeCap.Round))
            }

            Visual.BondIonic, Visual.BondCovalent, Visual.BondMetallic -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val cy = this.size.height / 2f
                drawCircle(accent, this.size.minDimension * 0.18f, Offset(this.size.width * 0.30f, cy))
                drawCircle(NeonPink, this.size.minDimension * 0.18f, Offset(this.size.width * 0.70f, cy))
                drawLine(NeonAmber, Offset(this.size.width * 0.30f, cy), Offset(this.size.width * 0.70f, cy), 3.5f * density, cap = StrokeCap.Round)
            }

            Visual.Electron -> Canvas(Modifier.fillMaxSize().padding(10.dp)) {
                val cy = this.size.height / 2f
                drawLine(accent, Offset(this.size.width * 0.20f, cy), Offset(this.size.width * 0.80f, cy), 3f * density, cap = StrokeCap.Round)
                for (i in 0..3) {
                    val x = this.size.width * (0.30f + i * 0.13f)
                    drawCircle(AcidGreen, 4f * density, Offset(x, cy))
                }
            }
        }
    }
}
