package com.vfg.silkroad.goo.ui.screens

import android.os.SystemClock
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.Subtopic
import com.vfg.silkroad.goo.data.Topic
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.SubtopicHero
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted

@Composable
fun SubtopicStudyScreen(
    topicId: String,
    subtopicId: String,
    contentPadding: PaddingValues,
    onBack: () -> Unit,
) {
    val topic: Topic = TopicsRepo.byId(topicId) ?: return
    val sub: Subtopic = TopicsRepo.subtopic(topicId, subtopicId) ?: return
    val accent = topic.accent

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
    ) {
        TopBar(title = topic.title, accent = accent, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Spacer(Modifier.height(4.dp))
            SubtopicHero(visual = sub.visual, accent = accent)
            Text(
                sub.title,
                style = MaterialTheme.typography.headlineMedium,
                color = OnDeepBlue,
                fontWeight = FontWeight.Bold
            )
            Text(
                sub.intro,
                style = MaterialTheme.typography.bodyLarge,
                color = OnDeepBlue
            )
            SectionHeader("Key facts", accent)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                sub.keyFacts.forEach { fact ->
                    BulletRow(text = fact, accent = accent)
                }
            }
            SectionHeader("Examples", accent)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                sub.examples.forEachIndexed { i, ex ->
                    ExampleCard(index = i + 1, text = ex, accent = accent)
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TopBar(title: String, accent: Color, onBack: () -> Unit) {
    val throttledBack = rememberThrottledClick(onClick = onBack)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.18f))
                .border(1.dp, accent.copy(alpha = 0.6f), CircleShape)
                .clickable { throttledBack() },
            contentAlignment = Alignment.Center
        ) {
            Text("‹", color = accent, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            title,
            color = accent,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        PulsingDot(accent)
    }
}

@Composable
private fun PulsingDot(color: Color) {
    val infinite = rememberInfiniteTransition(label = "dot")
    val a by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "a"
    )
    Box(
        Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = a))
    )
}

@Composable
private fun SectionHeader(text: String, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(width = 6.dp, height = 22.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(accent)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            color = OnDeepBlue,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun BulletRow(text: String, accent: Color) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            Modifier
                .padding(top = 8.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(accent)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            color = OnDeepBlue,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ExampleCard(index: Int, text: String, accent: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DeepBlueCard)
            .border(1.dp, accent.copy(alpha = 0.30f), RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.18f))
                .border(1.dp, accent.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(index.toString(), color = accent, style = MaterialTheme.typography.labelMedium)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            color = OnDeepBlue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun rememberThrottledClick(
    cooldownMs: Long = 1000L,
    onClick: () -> Unit,
): () -> Unit {
    val lastClick = remember { mutableLongStateOf(0L) }
    return {
        val now = SystemClock.uptimeMillis()
        if (now - lastClick.longValue >= cooldownMs) {
            lastClick.longValue = now
            onClick()
        }
    }
}