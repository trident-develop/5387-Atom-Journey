package com.vfg.silkroad.goo.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import com.vfg.silkroad.goo.ui.theme.ScoreExcellent
import com.vfg.silkroad.goo.ui.theme.ScoreGood
import com.vfg.silkroad.goo.ui.theme.ScoreWeak

@Composable
fun ScoreDial(
    percent: Int?,
    modifier: Modifier = Modifier,
    size: Dp = 88.dp,
    fallbackAccent: Color = OnDeepBlueMuted,
    label: String? = null,
) {
    val tierColor = when {
        percent == null -> fallbackAccent
        percent <= 40 -> ScoreWeak
        percent <= 70 -> ScoreGood
        else -> ScoreExcellent
    }
    val animPct by animateFloatAsState(
        targetValue = (percent ?: 0) / 100f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "dial"
    )
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = this.size.minDimension * 0.13f
            if (percent == null) {
                drawArc(
                    color = fallbackAccent.copy(alpha = 0.35f),
                    startAngle = 130f,
                    sweepAngle = 280f,
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(stroke * 0.7f, stroke * 0.7f))
                    )
                )
            } else {
                drawArc(
                    color = tierColor.copy(alpha = 0.16f),
                    startAngle = 130f,
                    sweepAngle = 280f,
                    useCenter = false,
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(tierColor.copy(alpha = 0.65f), tierColor),
                        center = Offset(this.size.width / 2f, this.size.height / 2f)
                    ),
                    startAngle = 130f,
                    sweepAngle = 280f * animPct,
                    useCenter = false,
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (percent == null) "—" else "${(animPct * 100).toInt()}%",
                color = if (percent == null) fallbackAccent else tierColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (label != null) {
                Text(
                    text = label,
                    color = if (percent == null) fallbackAccent else tierColor.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
