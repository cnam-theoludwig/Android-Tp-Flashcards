package com.example.tp_flashcard.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColorScheme = lightColorScheme(
    primary = AppPrimaryColor,
    secondary = AppSecondaryColor,
    background = AppBackground,
    surface = AppBackground,
    onPrimary = AppOnPrimaryColor,
    onSecondary = AppOnPrimaryColor,
    onBackground = AppTextColor,
    onSurface = AppTextColor
)

@Composable
fun TP_FlashcardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled to enforce the custom theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
