package com.vfg.silkroad.goo.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.Question
import com.vfg.silkroad.goo.data.QuestionsRepo
import com.vfg.silkroad.goo.data.Topic
import com.vfg.silkroad.goo.data.TopicsRepo
import com.vfg.silkroad.goo.ui.components.GradientProgress
import com.vfg.silkroad.goo.ui.components.rememberThrottledClick
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonRed
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TEST_DURATION_SECONDS = 300

private fun shuffleForRun(source: List<Question>): List<Question> =
    source.shuffled().map { original ->
        val pairs = original.options.mapIndexed { idx, opt -> idx to opt }.shuffled()
        Question(
            text = original.text,
            options = pairs.map { it.second },
            correctIndex = pairs.indexOfFirst { it.first == original.correctIndex }
        )
    }

@Composable
fun TestScreen(
    topicId: String,
    contentPadding: PaddingValues,
    onFinished: (correctCount: Int, total: Int, timeSpentSeconds: Int) -> Unit,
    onBack: () -> Unit,
) {
    val topic: Topic = TopicsRepo.byId(topicId) ?: return
    val questions = remember(topicId) { shuffleForRun(QuestionsRepo.forTopic(topicId)) }
    val total = questions.size
    if (total == 0) return

    var currentIndex by remember { mutableStateOf(0) }
    var correct by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf<Int?>(null) }
    var timeRemaining by remember { mutableStateOf(TEST_DURATION_SECONDS) }
    var finished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timeRemaining > 0 && !finished) {
            delay(1000)
            timeRemaining -= 1
        }
        if (!finished && timeRemaining <= 0) {
            finished = true
            onFinished(correct, total, TEST_DURATION_SECONDS)
        }
    }

    val scope = rememberCoroutineScope()
    val q: Question = questions[currentIndex]
    val accent = topic.accent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
    ) {
        TestTopBar(
            title = "${topic.title} Test",
            timeRemaining = timeRemaining,
            onBack = onBack
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Question ${currentIndex + 1} of $total",
                    color = ElectricCyan,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    "$correct correct",
                    color = AcidGreen,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(Modifier.height(8.dp))
            GradientProgress(
                progress = (currentIndex + (if (selected != null) 1 else 0)).toFloat() / total,
                glow = accent,
                accent = AcidGreen
            )
        }
        Spacer(Modifier.height(20.dp))
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                (slideInHorizontally(animationSpec = tween(280)) { it / 3 } + fadeIn(tween(280))) togetherWith
                    (slideOutHorizontally(animationSpec = tween(220)) { -it / 3 } + fadeOut(tween(220)))
            },
            label = "question",
            modifier = Modifier.weight(1f)
        ) { idx ->
            val current = questions[idx]
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(DeepBlueCard)
                            .border(1.dp, accent.copy(alpha = 0.40f), RoundedCornerShape(20.dp))
                            .padding(18.dp)
                    ) {
                        Text(
                            current.text,
                            color = OnDeepBlue,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                itemsIndexed(current.options) { optIdx, option ->
                    OptionRow(
                        text = option,
                        letter = ('A' + optIdx).toString(),
                        accent = accent,
                        state = when {
                            selected == null -> OptionState.Idle
                            optIdx == current.correctIndex -> OptionState.Correct
                            optIdx == selected -> OptionState.Wrong
                            else -> OptionState.Faded
                        },
                        enabled = selected == null,
                        onClick = {
                            if (selected != null || finished) return@OptionRow
                            val isCorrect = optIdx == current.correctIndex
                            if (isCorrect) correct += 1
                            selected = optIdx
                            scope.launch {
                                delay(650)
                                if (currentIndex + 1 >= total) {
                                    finished = true
                                    onFinished(
                                        correct,
                                        total,
                                        TEST_DURATION_SECONDS - timeRemaining
                                    )
                                } else {
                                    currentIndex += 1
                                    selected = null
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

private enum class OptionState { Idle, Correct, Wrong, Faded }

@Composable
private fun OptionRow(
    text: String,
    letter: String,
    accent: Color,
    state: OptionState,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val (border, bg, fg) = when (state) {
        OptionState.Idle -> Triple(accent.copy(alpha = 0.30f), DeepBlueElevated, OnDeepBlue)
        OptionState.Correct -> Triple(AcidGreen, AcidGreen.copy(alpha = 0.18f), AcidGreen)
        OptionState.Wrong -> Triple(NeonRed, NeonRed.copy(alpha = 0.18f), NeonRed)
        OptionState.Faded -> Triple(accent.copy(alpha = 0.10f), DeepBlueElevated.copy(alpha = 0.6f), OnDeepBlueMuted)
    }
    val scale = if (state == OptionState.Correct) 1.02f else 1f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .border(1.4.dp, border, RoundedCornerShape(16.dp))
            .let { if (enabled) it.clickable { onClick() } else it }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(border.copy(alpha = 0.20f))
                .border(1.dp, border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(letter, color = fg, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            color = fg,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TestTopBar(title: String, timeRemaining: Int, onBack: () -> Unit) {
    val urgent = timeRemaining < 30
    val infinite = rememberInfiniteTransition(label = "urgent")
    val pulse by infinite.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(550, easing = LinearEasing), RepeatMode.Reverse),
        label = "pulse"
    )
    val timerColor = if (urgent) NeonRed.copy(alpha = pulse) else ElectricCyan
    val mins = timeRemaining / 60
    val secs = timeRemaining % 60
    val throttledBack = rememberThrottledClick(onClick = onBack)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ElectricCyan.copy(alpha = 0.18f))
                .border(1.dp, ElectricCyan.copy(alpha = 0.6f), CircleShape)
                .clickable { throttledBack() },
            contentAlignment = Alignment.Center
        ) { Text("‹", color = ElectricCyan, fontWeight = FontWeight.Bold) }
        Spacer(Modifier.width(12.dp))
        Text(
            title,
            color = OnDeepBlue,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Box(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(timerColor.copy(alpha = 0.16f))
                .border(1.dp, timerColor, RoundedCornerShape(50))
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "%02d:%02d".format(mins, secs),
                color = timerColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
