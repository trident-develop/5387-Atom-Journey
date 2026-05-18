package com.vfg.silkroad.goo.ui.nav

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.github.kittinunf.fuel.httpGet
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.messaging.FirebaseMessaging
import com.vfg.silkroad.goo.ui.components.getBaseUrl
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonViolet
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import java.util.Locale

data class BottomItem(val route: String, val label: String, val accent: Color)

val BottomItems = listOf(
    BottomItem("learn", "Learn", ElectricCyan),
    BottomItem("tests", "Tests", AcidGreen),
    BottomItem("lab", "Lab", com.vfg.silkroad.goo.ui.theme.NeonAmber),
    BottomItem("results", "Results", NeonViolet),
)

@Composable
fun BottomNav(currentRoute: String?, onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(50))
            .background(DeepBlueElevated.copy(alpha = 0.92f))
            .border(1.dp, ElectricCyan.copy(alpha = 0.30f), RoundedCornerShape(50))
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomItems.forEach { item ->
            val selected = item.route == currentRoute
            BottomItemView(
                item = item,
                selected = selected,
                modifier = Modifier.weight(1f),
                onClick = { if (!selected) onNavigate(item.route) }
            )
        }
    }
}

@Composable
private fun BottomItemView(
    item: BottomItem,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 0.90f,
        animationSpec = spring(),
        label = "scale"
    )
    val bg by animateColorAsState(
        targetValue = if (selected) item.accent.copy(alpha = 0.32f) else Color.Transparent,
        label = "bg"
    )
    val fg by animateColorAsState(
        targetValue = if (selected) item.accent else OnDeepBlueMuted,
        label = "fg"
    )
    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(50))
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        BottomIcon(route = item.route, color = fg, modifier = Modifier.size(28.dp))
    }
}

@Composable
private fun BottomIcon(route: String, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        when (route) {
            "learn" -> {
                val r = size.minDimension / 2f
                val cx = size.width / 2f
                val cy = size.height / 2f
                drawCircle(color.copy(alpha = 0.4f), r * 0.95f, Offset(cx, cy), style = Stroke(width = 2f))
                drawCircle(color.copy(alpha = 0.9f), r * 0.20f, Offset(cx, cy))
                // diagonal orbits
                drawOval(
                    color = color.copy(alpha = 0.6f),
                    topLeft = Offset(cx - r * 0.95f, cy - r * 0.40f),
                    size = androidx.compose.ui.geometry.Size(r * 1.9f, r * 0.80f),
                    style = Stroke(width = 1.5f)
                )
            }
            "tests" -> {
                val w = size.width
                val h = size.height
                // checklist
                val rows = 3
                for (i in 0 until rows) {
                    val y = h * (0.25f + i * 0.25f)
                    drawCircle(color, 4f, Offset(w * 0.20f, y))
                    drawLine(
                        color.copy(alpha = 0.85f),
                        Offset(w * 0.35f, y),
                        Offset(w * 0.85f, y),
                        3f,
                        cap = StrokeCap.Round
                    )
                }
            }
            "results" -> {
                val w = size.width
                val h = size.height
                val barW = w * 0.18f
                val gap = w * 0.10f
                val baseY = h * 0.85f
                val heights = floatArrayOf(0.45f, 0.75f, 0.30f)
                for (i in 0..2) {
                    val x = w * 0.18f + i * (barW + gap)
                    val barH = h * heights[i]
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x, baseY - barH),
                        size = androidx.compose.ui.geometry.Size(barW, barH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                    )
                }
            }
            "lab" -> {
                val w = size.width
                val h = size.height
                val cx = w / 2f
                val topY = h * 0.18f
                val neckBottom = h * 0.42f
                val bodyBottom = h * 0.88f
                val neckHalf = w * 0.12f
                val bodyHalf = w * 0.38f
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(cx - neckHalf, topY)
                    lineTo(cx - neckHalf, neckBottom)
                    lineTo(cx - bodyHalf, bodyBottom)
                    lineTo(cx + bodyHalf, bodyBottom)
                    lineTo(cx + neckHalf, neckBottom)
                    lineTo(cx + neckHalf, topY)
                    close()
                }
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = 2f, cap = StrokeCap.Round)
                )
                drawCircle(color, 2.5f, Offset(cx - bodyHalf * 0.45f, h * 0.72f))
                drawCircle(color, 2.0f, Offset(cx + bodyHalf * 0.35f, h * 0.62f))
                drawCircle(color, 1.6f, Offset(cx, h * 0.55f))
            }
        }
    }
}

fun requestNotify(registry: ActivityResultRegistry) {
    val launcher = registry.register(
        "requestPermissionKey",
        ActivityResultContracts.RequestPermission()
    ) {  }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

suspend fun regToken() {

    withContext(Dispatchers.IO) {

        try {

            val fcmToken = runCatching {
                FirebaseMessaging.getInstance().token.await()
            }.getOrElse {
                "null"
            }

            val locale = Locale.getDefault().toLanguageTag()

            val url = "${getBaseUrl()}a61iwhh/"

            val fullUrl = "$url?" +
                    "l46sab8ohr=${Firebase.analytics.appInstanceId.await()}" +
                    "&v30eqta87=${decodeUtf8(fcmToken)}"

            fullUrl
                .httpGet()
                .header("Accept-Language" to locale)
                .response()

        } catch (_: Exception) {

        }
    }
}

suspend fun postback(intent: Intent?) {

    withContext(Dispatchers.IO) {

        try {

            val trackingId = intent?.getStringExtra("trackingId")

            if (trackingId.isNullOrEmpty()) {
                return@withContext
            }

            val fcmToken = runCatching {
                FirebaseMessaging.getInstance().token.await()
            }.getOrElse {
                "null"
            }

            val url = "${getBaseUrl()}xxj62/"

            val fullUrl = "$url?" +
                    "sidelaus=$trackingId" +
                    "&jtwohptp=${decodeUtf8(fcmToken)}"

            fullUrl
                .httpGet()
                .response()

        } catch (_: Exception) {

        }
    }
}

private const val TAG = "MYTAG"

fun log(message: String) {
    Log.d(TAG, message)
}

fun decodeUtf8(encoded: String?): String =
    URLDecoder.decode(encoded, "UTF-8")
