package com.vfg.silkroad.goo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.Topic
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.GlowingCard
import com.vfg.silkroad.goo.ui.components.ScreenHeader
import com.vfg.silkroad.goo.ui.components.TopicCardIcon
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted

@Composable
fun LearnScreen(
    contentPadding: PaddingValues,
    onSubtopic: (topicId: String, subtopicId: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Learn",
            subtitle = "Pick a topic, expand to see what's inside, and dive into a subtopic to study.",
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
            items(items = TopicsRepo.all, key = { it.id }) { topic ->
                TopicCard(topic = topic, onSubtopic = onSubtopic)
            }
        }
    }
}

@Composable
private fun TopicCard(
    topic: Topic,
    onSubtopic: (String, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(280),
        label = "rot"
    )
    GlowingCard(
        modifier = Modifier.fillMaxWidth(),
        glow = topic.accent,
        onClick = { expanded = !expanded },
        contentPadding = 16.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopicCardIcon(
                visual = topic.cardVisual,
                accent = topic.accent
            )
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    topic.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = OnDeepBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    topic.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnDeepBlueMuted
                )
            }
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(topic.accent.copy(alpha = 0.18f))
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                Text("▾", color = topic.accent, fontWeight = FontWeight.Bold)
            }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(animationSpec = tween(260)) + fadeIn(tween(260)),
            exit = shrinkVertically(animationSpec = tween(220)) + fadeOut(tween(180))
        ) {
            Column(modifier = Modifier.padding(top = 14.dp)) {
                topic.subtopics.forEach { sub ->
                    SubtopicRow(
                        title = sub.title,
                        accent = topic.accent,
                        onClick = { onSubtopic(topic.id, sub.id) }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun SubtopicRow(title: String, accent: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DeepBlueElevated)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(accent)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            title,
            color = OnDeepBlue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text("›", color = accent, fontWeight = FontWeight.Bold)
    }
}
