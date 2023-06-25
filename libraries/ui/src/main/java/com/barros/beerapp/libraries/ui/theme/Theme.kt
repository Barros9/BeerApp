package com.barros.beerapp.libraries.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorPalette = lightColorScheme(
    primary = Blue600,
    primaryContainer = Blue800,
    secondary = Orange800,
    secondaryContainer = Orange800
)

private val darkColorPalette = darkColorScheme(
    primary = Blue200,
    primaryContainer = Blue800,
    secondary = Orange300,
    secondaryContainer = Orange300
)

@Composable
fun BeerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorPalette else lightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
