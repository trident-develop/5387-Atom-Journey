package com.vfg.silkroad.goo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ChemistryColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = DeepBlue,
    secondary = AcidGreen,
    onSecondary = DeepBlue,
    tertiary = NeonViolet,
    onTertiary = OnDeepBlue,
    background = DeepBlue,
    onBackground = OnDeepBlue,
    surface = DeepBlueElevated,
    onSurface = OnDeepBlue,
    surfaceVariant = DeepBlueCard,
    onSurfaceVariant = OnDeepBlueMuted,
    outline = NeonViolet,
    error = NeonRed,
    onError = OnDeepBlue,
)

@Composable
fun AtomJourneyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ChemistryColorScheme,
        typography = Typography,
        content = content
    )
}
