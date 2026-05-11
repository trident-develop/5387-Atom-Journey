package com.vfg.silkroad.goo.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.vfg.silkroad.goo.data.FormulaToken
import kotlin.math.absoluteValue

@Composable
fun FormulaText(
    tokens: List<FormulaToken>,
    color: Color,
    style: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier,
) {
    val annotated = buildFormulaAnnotated(tokens, style.fontSize)
    Text(text = annotated, style = style, color = color, modifier = modifier)
}

@Composable
@ReadOnlyComposable
private fun buildFormulaAnnotated(tokens: List<FormulaToken>, baseSize: TextUnit): AnnotatedString {
    val small = (baseSize.value * 0.62f).coerceAtLeast(9f).sp
    return buildAnnotatedString {
        tokens.forEach { tk ->
            when (tk) {
                is FormulaToken.Element -> {
                    append(tk.symbol)
                    if (tk.subscript > 1) {
                        withStyle(
                            SpanStyle(
                                baselineShift = BaselineShift.Subscript,
                                fontSize = small,
                                fontWeight = FontWeight.SemiBold,
                            )
                        ) {
                            append(tk.subscript.toString())
                        }
                    }
                }
                is FormulaToken.Charge -> {
                    val sign = if (tk.value >= 0) "+" else "−"
                    val mag = tk.value.absoluteValue
                    val magText = if (mag <= 1) "" else mag.toString()
                    withStyle(
                        SpanStyle(
                            baselineShift = BaselineShift.Superscript,
                            fontSize = small,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append("$magText$sign")
                    }
                }
                FormulaToken.Plus -> append("  +  ")
                FormulaToken.Arrow -> append("  →  ")
            }
        }
    }
}

fun isEmptyFormula(tokens: List<FormulaToken>): Boolean = tokens.isEmpty()
