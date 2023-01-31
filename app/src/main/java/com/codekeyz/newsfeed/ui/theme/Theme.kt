package com.codekeyz.newsfeed.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Gunmetal200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = EerieBlack
)

private val LightColorPalette = lightColors(
    primary = Gunmetal200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = EerieBlack,

    /* Other default colors to override
    surface = EerieBlack,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun NewsFeedTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}