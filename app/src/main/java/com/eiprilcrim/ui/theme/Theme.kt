package com.eiprilcrim.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PurpleAccent,
    onPrimary = NavyBackground,
    secondary = PurplePrimary,
    onSecondary = TextPrimary,
    tertiary = PurpleLight,
    background = NavyBackground,
    onBackground = TextPrimary,
    surface = NavyCard,
    onSurface = TextPrimary,
    surfaceVariant = NavySecondary,
    onSurfaceVariant = TextSecondary,
    error = ErrorColor
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = TextPrimary,
    secondary = PurpleAccent,
    onSecondary = NavyBackground,
    tertiary = PurpleLight,
    background = PurpleLight,
    onBackground = NavyBackground,
    surface = TextPrimary,
    onSurface = NavyBackground,
    error = ErrorColor
)

@Composable
fun EpiCrimTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
