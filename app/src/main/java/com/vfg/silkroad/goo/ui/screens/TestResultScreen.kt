package com.vfg.silkroad.goo.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.ResultsRepository
import com.vfg.silkroad.goo.data.TestResult
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.GradientProgress
import com.vfg.silkroad.goo.ui.components.StatTile
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted

@Composable
fun TestResultScreen(
    topicId: String,
    correct: Int,
    total: Int,
    timeSpentSeconds: Int,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    onClose: () -> Unit,
) {
    val topic = TopicsRepo.byId(topicId) ?: return
    val context = LocalContext.current
    val repo = remember { ResultsRepository.get(context) }
    var saved by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!saved) {
            repo.save(
                TestResult(
                    topicId = topicId,
                    correct = correct,
                    total = total,
                    timeSpentSeconds = timeSpentSeconds,
                    completedAt = System.currentTimeMillis()
                )
            )
            saved = true
        }
    }

    val percent = if (total == 0) 0 else correct * 100 / total
    val tier = when {
        percent <= 40 -> "Weak"
        percent <= 70 -> "Good"
        else -> "Excellent"
    }
    val tierColor = scoreColor(percent)
    val animPct by animateFloatAsState(
        targetValue = percent / 100f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "pct"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            "Test complete",
            color = ElectricCyan,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(
            topic.title,
            color = OnDeepBlue,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(28.dp))

        Box(
            modifier = Modifier.size(220.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.fillMaxSize()) {
                drawArc(
                    color = ElectricCyan.copy(alpha = 0.18f),
                    startAngle = 130f,
                    sweepAngle = 280f,
                    useCenter = false,
                    style = Stroke(width = 22f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = tierColor,
                    startAngle = 130f,
                    sweepAngle = 280f * animPct,
                    useCenter = false,
                    style = Stroke(width = 22f, cap = StrokeCap.Round)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${(animPct * 100).toInt()}%",
                    color = tierColor,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    tier,
                    color = tierColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Spacer(Modifier.height(28.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatTile(
                modifier = Modifier.weight(1f),
                label = "Correct",
                value = "$correct / $total",
                accent = AcidGreen
            )
            StatTile(
                modifier = Modifier.weight(1f),
                label = "Time",
                value = formatMmSs(timeSpentSeconds),
                accent = ElectricCyan
            )
        }
        Spacer(Modifier.height(20.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DeepBlueCard)
                .border(1.dp, tierColor.copy(alpha = 0.40f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "Performance",
                    color = OnDeepBlueMuted,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(Modifier.height(8.dp))
                GradientProgress(progress = animPct, glow = tierColor, accent = tierColor)
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(DeepBlueElevated)
                    .border(1.dp, ElectricCyan.copy(alpha = 0.5f), RoundedCornerShape(50))
                    .clickable { onClose() }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Done", color = ElectricCyan, fontWeight = FontWeight.SemiBold)
            }
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(topic.accent.copy(alpha = 0.20f))
                    .border(1.dp, topic.accent, RoundedCornerShape(50))
                    .clickable { onRetry() }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Retry", color = topic.accent, fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

private fun formatMmSs(s: Int): String = "%d:%02d".format(s / 60, s % 60)
