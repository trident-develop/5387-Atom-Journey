package com.vfg.silkroad.goo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.QuestionsRepo
import com.vfg.silkroad.goo.data.ResultsRepository
import com.vfg.silkroad.goo.data.Topic
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.GlowingCard
import com.vfg.silkroad.goo.ui.components.ScoreDial
import com.vfg.silkroad.goo.ui.components.ScreenHeader
import com.vfg.silkroad.goo.ui.components.TopicCardIcon
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import com.vfg.silkroad.goo.ui.theme.ScoreExcellent
import com.vfg.silkroad.goo.ui.theme.ScoreGood
import com.vfg.silkroad.goo.ui.theme.ScoreWeak

@Composable
fun TestsScreen(
    contentPadding: PaddingValues,
    onStartTest: (topicId: String) -> Unit,
) {
    val context = LocalContext.current
    val repo = ResultsRepository.get(context)

    val totalTaken = repo.results.size
    val avg = if (totalTaken == 0) null else repo.averagePercent()
    val bestPct = repo.results.maxOfOrNull { it.percent }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Tests",
            subtitle = "Tap a tile to begin. 20 questions, 5-minute timer per test.",
            topInset = contentPadding.calculateTopPadding(),
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(
                top = 4.dp,
                bottom = contentPadding.calculateBottomPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(2) }) {
                StatsBanner(taken = totalTaken, avg = avg, best = bestPct)
            }
            items(items = TopicsRepo.all, key = { it.id }) { topic ->
                TestTile(
                    topic = topic,
                    bestPercent = repo.bestFor(topic.id)?.percent,
                    onStart = { onStartTest(topic.id) }
                )
            }
        }
    }
}

@Composable
private fun StatsBanner(taken: Int, avg: Int?, best: Int?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    listOf(DeepBlueCard, DeepBlueElevated)
                )
            )
            .border(1.dp, ElectricCyan.copy(alpha = 0.30f), RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatCol(label = "Taken", value = taken.toString(), accent = ElectricCyan)
        Divider()
        StatCol(label = "Avg", value = avg?.let { "$it%" } ?: "—", accent = AcidGreen)
        Divider()
        StatCol(label = "Best", value = best?.let { "$it%" } ?: "—", accent = NeonAmber)
    }
}

@Composable
private fun StatCol(label: String, value: String, accent: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = accent, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(label, color = OnDeepBlueMuted, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .width(1.dp)
            .height(28.dp)
            .background(ElectricCyan.copy(alpha = 0.20f))
    )
}

@Composable
private fun TestTile(
    topic: Topic,
    bestPercent: Int?,
    onStart: () -> Unit,
) {
    GlowingCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.70f),
        glow = topic.accent,
        cornerRadius = 20.dp,
        contentPadding = 12.dp,
        onClick = onStart,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TopicCardIcon(
                    visual = topic.cardVisual,
                    accent = topic.accent,
                    size = 36.dp
                )
                Text("▶", color = topic.accent.copy(alpha = 0.85f), fontWeight = FontWeight.Bold)
            }
            Text(
                topic.title,
                color = OnDeepBlue,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                minLines = 2
            )
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ScoreDial(
                    percent = bestPercent,
                    size = 78.dp,
                    fallbackAccent = OnDeepBlueMuted,
                    label = null
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetaPill(text = "${QuestionsRepo.forTopic(topic.id).size} Q", accent = topic.accent)
                MetaPill(text = "5:00", accent = topic.accent)
            }
        }
    }
}

@Composable
private fun MetaPill(text: String, accent: Color) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(accent.copy(alpha = 0.14f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text, color = accent, style = MaterialTheme.typography.labelSmall)
    }
}

internal fun scoreColor(percent: Int) = when {
    percent <= 40 -> ScoreWeak
    percent <= 70 -> ScoreGood
    else -> ScoreExcellent
}
