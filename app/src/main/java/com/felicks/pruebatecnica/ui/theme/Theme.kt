package com.felicks.pruebatecnica.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BilleGreenPrimary,
    secondary = BilleGreenLight,
    tertiary = BilleAmberWarning,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BilleGreenPrimary,
    secondary = BilleGreenLight,
    tertiary = BilleAmberWarning,
    background = BilleBackground,
    surface = BilleSurface,
    onPrimary = Color.White,
    onSecondary = BilleGreenDark,
    onTertiary = Color.Black,
    onBackground = BilleTextPrimary,
    onSurface = BilleTextPrimary
)

@Composable
fun PruebaTecnicaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}