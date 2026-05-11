package com.vfg.silkroad.goo.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vfg.silkroad.goo.data.FormulaToken
import com.vfg.silkroad.goo.data.FormulaEntry
import com.vfg.silkroad.goo.data.NotesRepository
import com.vfg.silkroad.goo.ui.components.FormulaText
import com.vfg.silkroad.goo.ui.components.GlowingCard
import com.vfg.silkroad.goo.ui.components.ScreenHeader
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
import java.text.DateFormat
import java.util.Date

private val Elements: List<Pair<Int, String>> = listOf(
    1 to "H", 2 to "He", 3 to "Li", 4 to "Be", 5 to "B",
    6 to "C", 7 to "N", 8 to "O", 9 to "F", 10 to "Ne",
    11 to "Na", 12 to "Mg", 13 to "Al", 14 to "Si", 15 to "P",
    16 to "S", 17 to "Cl", 18 to "Ar", 19 to "K", 20 to "Ca",
    26 to "Fe", 29 to "Cu", 30 to "Zn", 35 to "Br", 47 to "Ag",
    50 to "Sn", 53 to "I", 79 to "Au", 80 to "Hg", 82 to "Pb"
)

@Composable
fun LabScreen(contentPadding: PaddingValues) {
    val context = LocalContext.current
    val repo = remember { NotesRepository.get(context) }

    val tokens: SnapshotStateList<FormulaToken> = remember { mutableStateListOf<FormulaToken>() }
    var note by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager: FocusManager = LocalFocusManager.current

    fun appendElement(symbol: String) {
        focusManager.clearFocus()
        tokens.add(FormulaToken.Element(symbol, 1))
    }
    fun bumpSubscript(delta: Int) {
        focusManager.clearFocus()
        val idx = tokens.indexOfLast { it is FormulaToken.Element }
        if (idx == -1) return
        val el = tokens[idx] as FormulaToken.Element
        val newSub = (el.subscript + delta).coerceIn(1, 99)
        if (newSub != el.subscript) tokens[idx] = el.copy(subscript = newSub)
    }
    fun bumpCharge(delta: Int) {
        focusManager.clearFocus()
        val last = tokens.lastOrNull()
        if (last is FormulaToken.Charge) {
            val v = last.value + delta
            if (v == 0) tokens.removeAt(tokens.size - 1)
            else tokens[tokens.size - 1] = FormulaToken.Charge(v)
        } else if (delta != 0) {
            tokens.add(FormulaToken.Charge(delta))
        }
    }
    fun addPlus() {
        focusManager.clearFocus()
        if (tokens.isNotEmpty() && tokens.last() != FormulaToken.Plus && tokens.last() != FormulaToken.Arrow) {
            tokens.add(FormulaToken.Plus)
        }
    }
    fun addArrow() {
        focusManager.clearFocus()
        if (tokens.isNotEmpty() && tokens.last() != FormulaToken.Arrow) {
            tokens.add(FormulaToken.Arrow)
        }
    }
    fun backspace() {
        focusManager.clearFocus()
        if (tokens.isNotEmpty()) tokens.removeAt(tokens.size - 1)
    }
    fun clearAll() {
        focusManager.clearFocus()
        tokens.clear()
    }
    fun saveEntry() {
        if (tokens.isEmpty()) return
        repo.save(FormulaEntry.create(tokens.toList(), note.trim()))
        tokens.clear()
        note = ""
        focusManager.clearFocus()
        keyboard?.hide()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Lab",
            subtitle = "Build formulas atom by atom and pin them with a quick note.",
            topInset = contentPadding.calculateTopPadding(),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                },
            contentPadding = PaddingValues(
                top = 4.dp,
                bottom = contentPadding.calculateBottomPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
            FlaskPreview(tokens = tokens)
        }
        item {
            ActionsRow(
                onSubscriptUp = { bumpSubscript(+1) },
                onSubscriptDown = { bumpSubscript(-1) },
                onChargePlus = { bumpCharge(+1) },
                onChargeMinus = { bumpCharge(-1) },
                onPlus = { addPlus() },
                onArrow = { addArrow() },
                onBackspace = { backspace() },
                onClear = { clearAll() },
            )
        }
        item {
            ElementPalette(onPick = { appendElement(it) })
        }
        item {
            NoteField(value = note, onChange = { note = it })
        }
        item {
            SaveBar(
                enabled = tokens.isNotEmpty(),
                onSave = { saveEntry() }
            )
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Saved",
                    color = OnDeepBlue,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "${repo.entries.size}",
                    color = ElectricCyan,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
            if (repo.entries.isEmpty()) {
                item {
                    EmptySavedHint()
                }
            } else {
                items(items = repo.entries, key = { it.id }) { entry ->
                    SavedRow(entry = entry, onDelete = { repo.delete(entry.id) })
                }
            }
        }
    }
}

/* ---------- Flask preview ---------- */

@Composable
private fun FlaskPreview(tokens: SnapshotStateList<FormulaToken>) {
    val infinite = rememberInfiniteTransition(label = "flask")
    val bubble by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2400, easing = FastOutSlowInEasing), RepeatMode.Restart),
        label = "bubble"
    )
    val glow by infinite.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow"
    )
    val accent = ElectricCyan

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.linearGradient(listOf(DeepBlueCard, DeepBlueElevated)))
            .border(1.dp, accent.copy(alpha = 0.5f * glow), RoundedCornerShape(22.dp))
    ) {
        Canvas(Modifier.fillMaxSize().padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) {
            // Erlenmeyer flask outline
            val w = size.minDimension * 0.78f
            val cx = w / 2f + 8f
            val topY = size.height * 0.08f
            val neckBottom = size.height * 0.30f
            val neckHalf = w * 0.18f
            val bodyHalf = w * 0.42f
            val bodyBottom = size.height * 0.92f

            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(cx - neckHalf, topY)
                lineTo(cx - neckHalf, neckBottom)
                lineTo(cx - bodyHalf, bodyBottom)
                lineTo(cx + bodyHalf, bodyBottom)
                lineTo(cx + neckHalf, neckBottom)
                lineTo(cx + neckHalf, topY)
            }
            drawPath(
                path = path,
                color = accent.copy(alpha = 0.65f),
                style = Stroke(width = 3f * density, cap = StrokeCap.Round)
            )

            // liquid fill
            val liquidTop = size.height * 0.55f
            val liquid = androidx.compose.ui.graphics.Path().apply {
                moveTo(cx - bodyHalf * (liquidTop - neckBottom) / (bodyBottom - neckBottom) - (bodyHalf - neckHalf) * (liquidTop - neckBottom) / (bodyBottom - neckBottom), liquidTop)
                moveTo(cx - bodyHalf * 0.85f, liquidTop)
                lineTo(cx - bodyHalf, bodyBottom)
                lineTo(cx + bodyHalf, bodyBottom)
                lineTo(cx + bodyHalf * 0.85f, liquidTop)
                close()
            }
            drawPath(
                path = liquid,
                brush = Brush.verticalGradient(
                    listOf(accent.copy(alpha = 0.35f), AcidGreen.copy(alpha = 0.50f))
                )
            )

            // bubbles
            for (i in 0 until 4) {
                val phase = ((bubble + i * 0.25f) % 1f)
                val by = bodyBottom - (bodyBottom - liquidTop) * phase
                val bx = cx + (i - 1.5f) * bodyHalf * 0.35f
                val br = (4f + i * 1.2f) * density
                drawCircle(
                    color = Color.White.copy(alpha = (0.5f * (1f - phase)).coerceAtLeast(0f)),
                    radius = br,
                    center = Offset(bx, by)
                )
            }
        }

        // Formula text overlay
        Box(
            Modifier
                .fillMaxSize()
                .padding(start = 110.dp, end = 20.dp, top = 18.dp, bottom = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            if (tokens.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Build a formula",
                        color = OnDeepBlueMuted,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Tap an element below to begin.",
                        color = OnDeepBlueMuted.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                FormulaText(
                    tokens = tokens,
                    color = accent,
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

/* ---------- Actions row ---------- */

@Composable
private fun ActionsRow(
    onSubscriptUp: () -> Unit,
    onSubscriptDown: () -> Unit,
    onChargePlus: () -> Unit,
    onChargeMinus: () -> Unit,
    onPlus: () -> Unit,
    onArrow: () -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActionChip("n₊", AcidGreen, modifier = Modifier.weight(1f), onClick = onSubscriptUp)
            ActionChip("n₋", AcidGreen.copy(alpha = 0.8f), modifier = Modifier.weight(1f), onClick = onSubscriptDown)
            ActionChip("+", NeonAmber, modifier = Modifier.weight(1f), onClick = onChargePlus)
            ActionChip("−", NeonAmber.copy(alpha = 0.8f), modifier = Modifier.weight(1f), onClick = onChargeMinus)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActionChip("+", NeonViolet, modifier = Modifier.weight(1f), onClick = onPlus, label = " + ")
            ActionChip("→", NeonViolet, modifier = Modifier.weight(1f), onClick = onArrow)
            ActionChip("⌫", ElectricCyan, modifier = Modifier.weight(1f), onClick = onBackspace)
            ActionChip("Clear", NeonRed, modifier = Modifier.weight(1f), onClick = onClear)
        }
    }
}

@Composable
private fun ActionChip(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.14f))
            .border(1.dp, color.copy(alpha = 0.55f), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ---------- Element palette ---------- */

@Composable
private fun ElementPalette(onPick: (String) -> Unit) {
    Column {
        Text(
            "Elements",
            color = OnDeepBlueMuted,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(items = Elements, key = { it.first }) { (number, symbol) ->
                ElementChip(number = number, symbol = symbol, onClick = { onPick(symbol) })
            }
        }
    }
}

@Composable
private fun ElementChip(number: Int, symbol: String, onClick: () -> Unit) {
    val accent = colorFor(number)
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.92f else 1f, label = "scale")
    Column(
        modifier = Modifier
            .scale(scale)
            .size(width = 64.dp, height = 76.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.linearGradient(
                    listOf(accent.copy(alpha = 0.18f), DeepBlueElevated)
                )
            )
            .border(1.2.dp, accent.copy(alpha = 0.65f), RoundedCornerShape(14.dp))
            .clickable {
                pressed = true
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            number.toString(),
            color = accent.copy(alpha = 0.85f),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            symbol,
            color = accent,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(0.dp))
    }
    // reset pressed
    if (pressed) {
        androidx.compose.runtime.LaunchedEffect(symbol, pressed) {
            kotlinx.coroutines.delay(120)
            pressed = false
        }
    }
}

private fun colorFor(z: Int): Color = when (z) {
    1, 2 -> ElectricCyan
    in 3..10 -> AcidGreen
    in 11..18 -> NeonAmber
    in 19..36 -> NeonViolet
    else -> NeonPink
}

/* ---------- Note field ---------- */

@Composable
private fun NoteField(value: String, onChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DeepBlueCard)
            .border(1.dp, ElectricCyan.copy(alpha = 0.30f), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Text(
            "Note",
            color = OnDeepBlueMuted,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(Modifier.height(6.dp))
        BasicTextField(
            value = value,
            onValueChange = onChange,
            textStyle = LocalTextStyle.current.copy(color = OnDeepBlue, fontSize = 15.sp),
            cursorBrush = Brush.verticalGradient(listOf(ElectricCyan, AcidGreen)),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                if (value.isEmpty()) {
                    Text(
                        "e.g. water at room temperature",
                        color = OnDeepBlueMuted.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                inner()
            }
        )
    }
}

/* ---------- Save bar ---------- */

@Composable
private fun SaveBar(enabled: Boolean, onSave: () -> Unit) {
    val alpha = if (enabled) 1f else 0.35f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(AcidGreen.copy(alpha = 0.18f * alpha))
            .border(1.2.dp, AcidGreen.copy(alpha = 0.65f * alpha), RoundedCornerShape(50))
            .clickable(enabled = enabled) { onSave() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Save to lab book",
            color = AcidGreen.copy(alpha = alpha),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/* ---------- Saved rows ---------- */

@Composable
private fun EmptySavedHint() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DeepBlueCard)
            .border(
                1.dp,
                ElectricCyan.copy(alpha = 0.20f),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(ElectricCyan.copy(alpha = 0.6f))
        )
        Spacer(Modifier.width(10.dp))
        Text(
            "Saved formulas show up here. Try H₂O, Na+Cl→NaCl, or Mg²⁺.",
            color = OnDeepBlueMuted,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SavedRow(entry: FormulaEntry, onDelete: () -> Unit) {
    GlowingCard(
        modifier = Modifier.fillMaxWidth(),
        glow = ElectricCyan,
        cornerRadius = 18.dp,
        contentPadding = 14.dp,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            FormulaText(
                tokens = entry.tokens,
                color = ElectricCyan,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(NeonRed.copy(alpha = 0.18f))
                    .border(1.dp, NeonRed.copy(alpha = 0.55f), CircleShape)
                    .clickable { onDelete() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("✕", color = NeonRed, style = MaterialTheme.typography.labelLarge)
            }
        }
        if (entry.note.isNotBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                entry.note,
                color = OnDeepBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(entry.createdAt)),
            color = OnDeepBlueMuted,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
