package com.vfg.silkroad.goo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.ResultsRepository
import com.vfg.silkroad.goo.data.Topic
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.AtomVisual
import com.vfg.silkroad.goo.ui.components.GlowingCard
import com.vfg.silkroad.goo.ui.components.GradientProgress
import com.vfg.silkroad.goo.ui.components.ScreenHeader
import com.vfg.silkroad.goo.ui.components.StatTile
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.NeonViolet
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import java.text.DateFormat
import java.util.Date

@Composable
fun ResultsScreen(contentPadding: PaddingValues) {
    val context = LocalContext.current
    val repo = remember { ResultsRepository.get(context) }
    val results = repo.results

    if (results.isEmpty()) {
        EmptyState(contentPadding)
        return
    }

    val total = results.size
    val avg = repo.averagePercent()
    val bestId = repo.bestTopicId()
    val weakId = repo.weakestTopicId()
    val bestTopicName = bestId?.let { TopicsRepo.byId(it)?.title } ?: "—"
    val weakTopicName = weakId?.let { TopicsRepo.byId(it)?.title } ?: "—"

    val perTopic: List<TopicSnapshot> = TopicsRepo.all.mapNotNull { topic ->
        val latest = repo.latestFor(topic.id) ?: return@mapNotNull null
        val best = repo.bestFor(topic.id)?.percent ?: latest.percent
        TopicSnapshot(topic, latest, best)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Results",
            subtitle = "Track how you've done across every topic.",
            topInset = contentPadding.calculateTopPadding(),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(
                top = 4.dp,
                bottom = contentPadding.calculateBottomPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatTile(
                        modifier = Modifier.weight(1f),
                        label = "Total tests",
                        value = total.toString(),
                        accent = ElectricCyan
                    )
                    StatTile(
                        modifier = Modifier.weight(1f),
                        label = "Average score",
                        value = "$avg%",
                        accent = AcidGreen
                    )
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatTile(
                        modifier = Modifier.weight(1f),
                        label = "Best topic",
                        value = bestTopicName,
                        accent = NeonAmber
                    )
                    StatTile(
                        modifier = Modifier.weight(1f),
                        label = "Weakest topic",
                        value = weakTopicName,
                        accent = NeonViolet
                    )
                }
            }
            item {
                Spacer(Modifier.height(2.dp))
                Text(
                    "Per topic",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnDeepBlue,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp)
                )
            }
            items(items = perTopic, key = { it.topic.id }) { snap ->
                ResultRow(snap)
            }
        }
    }
}

private data class TopicSnapshot(
    val topic: Topic,
    val latest: com.vfg.silkroad.goo.data.TestResult,
    val best: Int,
)

@Composable
private fun ResultRow(snap: TopicSnapshot) {
    val accent = snap.topic.accent
    val percent = snap.latest.percent
    val tier = scoreColor(percent)
    val date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(snap.latest.completedAt))

    GlowingCard(
        modifier = Modifier.fillMaxWidth(),
        glow = accent,
        contentPadding = 16.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(tier)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                snap.topic.title,
                style = MaterialTheme.typography.titleMedium,
                color = OnDeepBlue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Text(
                "$percent%",
                color = tier,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(10.dp))
        GradientProgress(progress = percent / 100f, glow = tier, accent = tier)
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MetaText("${snap.latest.correct} / ${snap.latest.total} correct")
            MetaText(formatMmSs(snap.latest.timeSpentSeconds))
            MetaText(date)
        }
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(50))
                    .background(accent.copy(alpha = 0.18f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    "Best: ${snap.best}%",
                    color = accent,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun MetaText(text: String) {
    Text(text, color = OnDeepBlueMuted, style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun EmptyState(contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(DeepBlueCard)
                .border(1.dp, ElectricCyan.copy(alpha = 0.4f), CircleShape)
                .padding(16.dp)
        ) {
            AtomVisual(modifier = Modifier.fillMaxSize())
        }
        Spacer(Modifier.height(20.dp))
        Text(
            "No tests completed yet",
            color = OnDeepBlue,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Take a test from the Tests tab and your scores will land here.",
            color = OnDeepBlueMuted,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun formatMmSs(s: Int): String = "%d:%02d".format(s / 60, s % 60)
