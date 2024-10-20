package com.dev.jetnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = ColorScheme(
    primary = Color(0xFFC09BD8),           // Wisteria (slightly lighter in dark mode)
    onPrimary = Color(0xFF000000),         // Black for contrast
    primaryContainer = Color(0xFF9F84BD),  // African Violet (darker)
    onPrimaryContainer = Color(0xFFFFFFFF), // White for contrast
    inversePrimary = Color(0xFFC09BD8),    // Wisteria

    secondary = Color(0xFFEBC3DB),         // Fairy Tale
    onSecondary = Color(0xFF000000),       // Black for contrast
    secondaryContainer = Color(0xFF9F84BD), // African Violet (darker)
    onSecondaryContainer = Color(0xFFFFFFFF), // White for contrast

    tertiary = Color(0xFFEDE3E9),          // Lavender Blush (used as tertiary)
    onTertiary = Color(0xFF000000),        // Black for contrast
    tertiaryContainer = Color(0xFFC09BD8), // Wisteria (darker)
    onTertiaryContainer = Color(0xFFFFFFFF), // White for contrast

    background = Color(0xFF121212),        // Dark background for dark mode
    onBackground = Color(0xFFEDE3E9),      // Lighter Lavender Blush

    surface = Color(0xFF1E1E1E),           // Slightly lighter surface color
    onSurface = Color(0xFFE6E4CE),         // Eggshell
    surfaceVariant = Color(0xFF9F84BD),    // African Violet (darker)
    onSurfaceVariant = Color(0xFFEBC3DB),  // Fairy Tale

    surfaceTint = Color(0xFF9F84BD),       // African Violet (darker)

    inverseSurface = Color(0xFFE6E4CE),    // Eggshell (used as inverse)
    inverseOnSurface = Color(0xFF000000),  // Black for contrast

    error = Color(0xFFCF6679),             // Dark red for errors (Material dark theme standard)
    onError = Color(0xFF000000),           // Black for contrast
    errorContainer = Color(0xFFD32F2F),    // Slightly darker red
    onErrorContainer = Color(0xFFFFFFFF),  // White for contrast

    outline = Color(0xFF9F84BD),           // African Violet (as outline)
    outlineVariant = Color(0xFFC09BD8),    // Wisteria
    scrim = Color(0xFF000000) // Black for overlay/scrim
)

private val LightColorScheme = ColorScheme(
    primary = Color(0xFF9F84BD),           // African Violet
    onPrimary = Color(0xFFFFFFFF),         // White for contrast
    primaryContainer = Color(0xFFC09BD8),  // Wisteria
    onPrimaryContainer = Color(0xFF000000), // Black for contrast
    inversePrimary = Color(0xFF9F84BD),    // Same as primary

    secondary = Color(0xFFEBC3DB),         // Fairy Tale
    onSecondary = Color(0xFF000000),       // Black for contrast
    secondaryContainer = Color(0xFFEDE3E9), // Lavender Blush
    onSecondaryContainer = Color(0xFF000000), // Black for contrast

    tertiary = Color(0xFFE6E4CE),          // Eggshell
    onTertiary = Color(0xFF000000),        // Black for contrast
    tertiaryContainer = Color(0xFFEDE3E9), // Lavender Blush
    onTertiaryContainer = Color(0xFF000000), // Black for contrast

    background = Color(0xFFEDE3E9),        // Lavender Blush
    onBackground = Color(0xFF000000),      // Black for contrast

    surface = Color(0xFFE6E4CE),           // Eggshell
    onSurface = Color(0xFF000000),         // Black for contrast
    surfaceVariant = Color(0xFFC09BD8),    // Wisteria
    onSurfaceVariant = Color(0xFF000000),  // Black for contrast
    surfaceTint = Color(0xFF9F84BD),       // African Violet

    inverseSurface = Color(0xFF000000),    // Black for strong contrast
    inverseOnSurface = Color(0xFFFFFFFF),  // White for contrast

    error = Color(0xFFD32F2F),             // Default Material color for error (Red)
    onError = Color(0xFFFFFFFF),           // White for contrast
    errorContainer = Color(0xFFFFCDD2),    // Lighter red for error container
    onErrorContainer = Color(0xFF000000),  // Black for contrast

    outline = Color(0xFF9F84BD),           // African Violet
    outlineVariant = Color(0xFFC09BD8),    // Wisteria
    scrim = Color(0xFF000000)              // Black for overlay/scrim
)

@Composable
fun JetNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}