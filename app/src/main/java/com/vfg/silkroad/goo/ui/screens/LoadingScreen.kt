package com.vfg.silkroad.goo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.ui.components.AtomVisual
import com.vfg.silkroad.goo.ui.components.ChemistryBackground
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(onFinished: () -> Unit) {
    BackHandler(enabled = true) {}
    val infinite = rememberInfiniteTransition(label = "loading")
    val pulseScale by infinite.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            tween(1400, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val haloAlpha by infinite.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            tween(1800, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "halo"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        ChemistryBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1.1f))
            Box(contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .size(280.dp)
                        .scale(pulseScale)
                        .clip(RoundedCornerShape(50))
                ) {
                    AtomVisual(modifier = Modifier.fillMaxSize())
                }
                Box(
                    Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(50))
                        .scale(0.9f + haloAlpha * 0.15f)
                )
            }
            Spacer(Modifier.height(48.dp))
            Text(
                "Initializing the lab",
                style = MaterialTheme.typography.titleMedium,
                color = ElectricCyan,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Calibrating molecules and stabilizing reactions…",
                style = MaterialTheme.typography.bodyMedium,
                color = OnDeepBlueMuted,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(28.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                color = AcidGreen,
                trackColor = ElectricCyan.copy(alpha = 0.15f)
            )
            Spacer(Modifier.weight(1f))
        }
    }
}
