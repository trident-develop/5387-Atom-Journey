package com.vfg.silkroad.goo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vfg.silkroad.goo.R

val AtomFont = FontFamily(Font(R.font.font))

private fun base(
    size: Int,
    weight: FontWeight = FontWeight.Normal,
    line: Int = (size * 1.4f).toInt(),
    letter: Float = 0f
) = TextStyle(
    fontFamily = AtomFont,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = line.sp,
    letterSpacing = letter.sp
)

val Typography = Typography(
    displayLarge = base(40, FontWeight.Bold, 48),
    displayMedium = base(32, FontWeight.Bold, 40),
    displaySmall = base(26, FontWeight.Bold, 32),
    headlineLarge = base(26, FontWeight.SemiBold, 32),
    headlineMedium = base(22, FontWeight.SemiBold, 28),
    headlineSmall = base(19, FontWeight.SemiBold, 26),
    titleLarge = base(20, FontWeight.SemiBold, 26),
    titleMedium = base(17, FontWeight.SemiBold, 22),
    titleSmall = base(14, FontWeight.SemiBold, 18, 0.2f),
    bodyLarge = base(16, FontWeight.Normal, 24, 0.2f),
    bodyMedium = base(14, FontWeight.Normal, 20, 0.2f),
    bodySmall = base(12, FontWeight.Normal, 16, 0.3f),
    labelLarge = base(14, FontWeight.SemiBold, 20, 0.4f),
    labelMedium = base(12, FontWeight.SemiBold, 16, 0.4f),
    labelSmall = base(11, FontWeight.SemiBold, 14, 0.5f),
)
