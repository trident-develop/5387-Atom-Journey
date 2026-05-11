package com.vfg.silkroad.goo.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.NeonPink
import com.vfg.silkroad.goo.ui.theme.NeonRed
import com.vfg.silkroad.goo.ui.theme.NeonViolet
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/* ---------- Atom ---------- */

@Composable
fun AtomVisual(
    modifier: Modifier = Modifier,
    nucleusColor: Color = NeonAmber,
    orbitColor: Color = ElectricCyan,
    electronColor: Color = AcidGreen,
    orbits: Int = 3
) {
    val infinite = rememberInfiniteTransition(label = "atom")
    val rot by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing)),
        label = "rot"
    )
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val maxR = min(cx, cy) * 0.92f
        for (i in 0 until orbits) {
            val angle = i * 60f
            val rad = Math.toRadians(angle.toDouble())
            val rx = maxR
            val ry = maxR * 0.42f
            rotate(angle, Offset(cx, cy)) {
                drawOval(
                    color = orbitColor.copy(alpha = 0.55f),
                    topLeft = Offset(cx - rx, cy - ry),
                    size = Size(rx * 2, ry * 2),
                    style = Stroke(width = 2f)
                )
                val electronAngle = (rot + i * 120f) * (PI.toFloat() / 180f)
                val ex = cx + rx * cos(electronAngle)
                val ey = cy + ry * sin(electronAngle)
                drawCircle(
                    color = electronColor.copy(alpha = 0.35f),
                    radius = 14f * density,
                    center = Offset(ex, ey)
                )
                drawCircle(
                    color = electronColor,
                    radius = 6f * density,
                    center = Offset(ex, ey)
                )
            }
        }
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(nucleusColor.copy(alpha = 0.95f), nucleusColor.copy(alpha = 0.0f)),
                center = Offset(cx, cy),
                radius = maxR * 0.45f
            ),
            radius = maxR * 0.45f,
            center = Offset(cx, cy)
        )
        drawCircle(
            color = nucleusColor,
            radius = maxR * 0.16f,
            center = Offset(cx, cy)
        )
    }
}

/* ---------- Concentric electron shells ---------- */

@Composable
fun ElectronShellsVisual(
    modifier: Modifier = Modifier,
    electronsPerShell: List<Int> = listOf(2, 8, 8),
) {
    val infinite = rememberInfiniteTransition(label = "shells")
    val phase by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing)),
        label = "phase"
    )
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val maxR = min(cx, cy) * 0.95f
        val shells = electronsPerShell.size
        electronsPerShell.forEachIndexed { idx, count ->
            val r = maxR * (idx + 1f) / shells
            drawCircle(
                color = ElectricCyan.copy(alpha = 0.45f),
                radius = r,
                center = Offset(cx, cy),
                style = Stroke(width = 1.6f)
            )
            for (k in 0 until count) {
                val a = (phase * (1f - idx * 0.15f) + k * 360f / count) * (PI.toFloat() / 180f)
                val ex = cx + r * cos(a)
                val ey = cy + r * sin(a)
                drawCircle(AcidGreen.copy(alpha = 0.35f), 9f * density, Offset(ex, ey))
                drawCircle(AcidGreen, 4.2f * density, Offset(ex, ey))
            }
        }
        drawCircle(NeonAmber, maxR * 0.13f, Offset(cx, cy))
    }
}

/* ---------- Molecule (two atoms with bond) ---------- */

@Composable
fun MoleculeVisual(
    modifier: Modifier = Modifier,
    leftLabel: String = "H",
    rightLabel: String = "O",
    leftColor: Color = ElectricCyan,
    rightColor: Color = NeonPink,
    bondColor: Color = AcidGreen,
    doubleBond: Boolean = false
) {
    val infinite = rememberInfiniteTransition(label = "mol")
    val pulse by infinite.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1600, easing = LinearEasing), androidx.compose.animation.core.RepeatMode.Reverse),
        label = "pulse"
    )
    Box(modifier = modifier.aspectRatio(2.2f)) {
        Canvas(Modifier.fillMaxSize()) {
            val cy = size.height / 2f
            val left = Offset(size.width * 0.22f, cy)
            val right = Offset(size.width * 0.78f, cy)
            val r = min(size.width, size.height) * 0.18f
            drawLine(
                color = bondColor.copy(alpha = pulse),
                start = left,
                end = right,
                strokeWidth = 6f * density,
                cap = StrokeCap.Round
            )
            if (doubleBond) {
                drawLine(
                    color = bondColor.copy(alpha = pulse * 0.8f),
                    start = left.copy(y = cy - 12f * density),
                    end = right.copy(y = cy - 12f * density),
                    strokeWidth = 4f * density,
                    cap = StrokeCap.Round
                )
            }
            drawCircle(leftColor.copy(alpha = 0.35f), r * 1.55f, left)
            drawCircle(leftColor, r, left)
            drawCircle(rightColor.copy(alpha = 0.35f), r * 1.55f, right)
            drawCircle(rightColor, r, right)
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(leftLabel, color = Color(0xFF03102B), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(rightLabel, color = Color(0xFF03102B), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
        }
    }
}

/* ---------- pH scale ---------- */

@Composable
fun PhScaleVisual(modifier: Modifier = Modifier, value: Float = 7f) {
    val v = value.coerceIn(0f, 14f)
    Box(modifier = modifier.fillMaxWidth()) {
        Canvas(Modifier.fillMaxWidth().height(34.dp)) {
            val grad = Brush.horizontalGradient(
                listOf(
                    NeonRed,
                    Color(0xFFFF8A3D),
                    NeonAmber,
                    AcidGreen,
                    ElectricCyan,
                    NeonViolet,
                    Color(0xFF6E3DFF)
                )
            )
            drawRoundRect(
                brush = grad,
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f, 20f)
            )
            val markerX = size.width * (v / 14f)
            drawCircle(Color.White, 10f * density, Offset(markerX, size.height / 2f))
            drawCircle(Color.Black.copy(alpha = 0.4f), 6f * density, Offset(markerX, size.height / 2f))
        }
        Row(
            Modifier.fillMaxWidth().padding(top = 38.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("0  acid", color = OnDeepBlueMuted, fontSize = 12.sp)
            Text("7  neutral", color = OnDeepBlueMuted, fontSize = 12.sp)
            Text("14  base", color = OnDeepBlueMuted, fontSize = 12.sp)
        }
    }
}

/* ---------- Energy diagram ---------- */

@Composable
fun EnergyDiagramVisual(modifier: Modifier = Modifier, exothermic: Boolean = true) {
    Canvas(modifier = modifier.fillMaxWidth().height(160.dp)) {
        val w = size.width
        val h = size.height
        val left = 24f
        val right = w - 24f
        val baseLine = h * 0.85f
        // axes
        drawLine(OnDeepBlueMuted, Offset(left, h * 0.05f), Offset(left, baseLine), 2f)
        drawLine(OnDeepBlueMuted, Offset(left, baseLine), Offset(right, baseLine), 2f)
        // reactant level
        val reactantY = if (exothermic) h * 0.30f else h * 0.55f
        val productY = if (exothermic) h * 0.65f else h * 0.20f
        val peakY = h * 0.10f
        // curve via cubic-like polyline
        val steps = 64
        val path = androidx.compose.ui.graphics.Path()
        path.moveTo(left + 20f, reactantY)
        path.lineTo(w * 0.30f, reactantY)
        for (i in 0..steps) {
            val tt = i / steps.toFloat()
            val x = w * 0.30f + (w * 0.40f) * tt
            val y = reactantY + (peakY - reactantY) * sin(tt * PI.toFloat()) - (peakY - reactantY) * 0f
            path.lineTo(x, y)
        }
        path.lineTo(w * 0.70f, productY)
        path.lineTo(right - 8f, productY)
        drawPath(
            path = path,
            color = ElectricCyan,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
        // labels
        drawCircle(AcidGreen, 7f, Offset(w * 0.20f, reactantY))
        drawCircle(NeonPink, 7f, Offset(w * 0.85f, productY))
        // ΔH arrow
        val dhX = w * 0.86f
        drawLine(
            color = if (exothermic) AcidGreen else NeonRed,
            start = Offset(dhX, reactantY),
            end = Offset(dhX, productY),
            strokeWidth = 3f
        )
    }
}

/* ---------- Reaction arrow with reactant → product ---------- */

@Composable
fun ReactionVisual(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "rxn")
    val flow by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "flow"
    )
    Box(modifier = modifier.fillMaxWidth().height(110.dp)) {
        Canvas(Modifier.fillMaxSize()) {
            val cy = size.height / 2f
            val r = 22f * density
            // reactant circles
            drawCircle(ElectricCyan, r, Offset(size.width * 0.10f, cy))
            drawCircle(AcidGreen, r, Offset(size.width * 0.18f, cy + 18f))
            // arrow
            val ax1 = size.width * 0.30f
            val ax2 = size.width * 0.70f
            drawLine(NeonViolet, Offset(ax1, cy), Offset(ax2, cy), 4f, StrokeCap.Round)
            drawLine(NeonViolet, Offset(ax2, cy), Offset(ax2 - 18f, cy - 12f), 4f, StrokeCap.Round)
            drawLine(NeonViolet, Offset(ax2, cy), Offset(ax2 - 18f, cy + 12f), 4f, StrokeCap.Round)
            // moving particle
            val px = ax1 + (ax2 - ax1) * flow
            drawCircle(NeonAmber, 6f * density, Offset(px, cy - 10f))
            // product
            drawCircle(NeonPink, r * 1.2f, Offset(size.width * 0.85f, cy))
        }
    }
}

/* ---------- Periodic element cell ---------- */

@Composable
fun PeriodicCellVisual(
    modifier: Modifier = Modifier,
    number: Int,
    symbol: String,
    name: String,
    accent: Color = ElectricCyan
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.linearGradient(
                    listOf(DeepBlueCard, DeepBlueElevated)
                )
            )
            .border(1.4.dp, accent.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Text(number.toString(), color = OnDeepBlueMuted, fontSize = 12.sp)
        Text(symbol, color = accent, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Text(name, color = OnDeepBlue, fontSize = 12.sp)
    }
}

/* ---------- Bond style mini-visual ---------- */

@Composable
fun BondTypeVisual(
    modifier: Modifier = Modifier,
    type: BondType
) {
    Box(modifier = modifier.fillMaxWidth().height(96.dp)) {
        Canvas(Modifier.fillMaxSize()) {
            val cy = size.height / 2f
            val left = Offset(size.width * 0.25f, cy)
            val right = Offset(size.width * 0.75f, cy)
            val r = min(size.width, size.height) * 0.18f
            when (type) {
                BondType.Ionic -> {
                    drawCircle(ElectricCyan, r, left)
                    drawCircle(NeonPink, r, right)
                    drawLine(
                        NeonAmber,
                        left.copy(x = left.x + r),
                        right.copy(x = right.x - r),
                        3f * density,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 14f))
                    )
                }
                BondType.Covalent -> {
                    drawCircle(ElectricCyan, r, left)
                    drawCircle(AcidGreen, r, right)
                    drawLine(NeonAmber, left.copy(x = left.x + r), right.copy(x = right.x - r), 4f * density)
                }
                BondType.Metallic -> {
                    for (i in 0..3) {
                        for (j in 0..1) {
                            val cx = size.width * (0.25f + i * 0.17f)
                            val cyy = cy - 18f + j * 36f
                            drawCircle(ElectricCyan, r * 0.7f, Offset(cx, cyy))
                        }
                    }
                    for (k in 0..6) {
                        val ex = size.width * (0.18f + k * 0.10f)
                        val dy = if (k % 2 == 0) -6f else 6f
                        drawCircle(AcidGreen, 5f * density, Offset(ex, cy + dy))
                    }
                }
            }
        }
    }
}

enum class BondType { Ionic, Covalent, Metallic }

/* ---------- Electron flow (galvanic cell-ish) ---------- */

@Composable
fun ElectronFlowVisual(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "ef")
    val flow by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2400, easing = LinearEasing)),
        label = "flow"
    )
    Canvas(modifier = modifier.fillMaxWidth().height(140.dp)) {
        val w = size.width
        val h = size.height
        val left = w * 0.18f
        val right = w * 0.82f
        val top = h * 0.25f
        val bottom = h * 0.75f
        // electrodes
        drawRect(NeonViolet, topLeft = Offset(left - 6f, top - 10f), size = Size(12f, bottom - top + 20f))
        drawRect(NeonViolet, topLeft = Offset(right - 6f, top - 10f), size = Size(12f, bottom - top + 20f))
        // wire
        drawLine(OnDeepBlueMuted, Offset(left, top), Offset(left, h * 0.10f), 3f)
        drawLine(OnDeepBlueMuted, Offset(left, h * 0.10f), Offset(right, h * 0.10f), 3f)
        drawLine(OnDeepBlueMuted, Offset(right, h * 0.10f), Offset(right, top), 3f)
        // electrons flowing along wire
        for (k in 0..5) {
            val seg = ((flow + k / 6f) % 1f)
            val (x, y) = when {
                seg < 0.33f -> {
                    val t2 = seg / 0.33f
                    left to (top + (h * 0.10f - top) * (1 - t2))
                }
                seg < 0.67f -> {
                    val t2 = (seg - 0.33f) / 0.34f
                    (left + (right - left) * t2) to h * 0.10f
                }
                else -> {
                    val t2 = (seg - 0.67f) / 0.33f
                    right to (h * 0.10f + (top - h * 0.10f) * t2)
                }
            }
            drawCircle(AcidGreen, 5f * density, Offset(x, y))
        }
        // electrolyte solution
        drawLine(ElectricCyan.copy(alpha = 0.5f), Offset(left + 6f, bottom + 4f), Offset(right - 6f, bottom + 4f), 4f)
    }
}

/* ---------- Animated linear progress with gradient ---------- */

@Composable
fun GradientProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    height: androidx.compose.ui.unit.Dp = 8.dp,
    glow: Color = ElectricCyan,
    accent: Color = AcidGreen
) {
    val p = progress.coerceIn(0f, 1f)
    val pAnim by androidx.compose.animation.core.animateFloatAsState(p, label = "progress")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(DeepBlueElevated)
    ) {
        Box(
            Modifier
                .fillMaxWidth(pAnim)
                .height(height)
                .clip(RoundedCornerShape(50))
                .background(Brush.horizontalGradient(listOf(glow, accent)))
        )
    }
}

/* ---------- Stat tile ---------- */

@Composable
fun StatTile(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    accent: Color = ElectricCyan
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(DeepBlueCard)
            .border(1.dp, accent.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Text(label, color = OnDeepBlueMuted, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(6.dp))
        Text(value, color = accent, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
    }
}
