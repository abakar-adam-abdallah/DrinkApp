package com.supdevinci.drinkapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = BlueSecondaryDark,
    tertiary = AccentSoftDark,

    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = TextPrimaryLight,
    onSecondary = TextPrimaryLight,
    onTertiary = TextPrimaryLight,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    error = ErrorRed
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = AccentSoft,

    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = WhiteText,
    onSecondary = WhiteText,
    onTertiary = TextPrimaryLight,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    error = ErrorRed
)

@Composable
fun DrinkAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}