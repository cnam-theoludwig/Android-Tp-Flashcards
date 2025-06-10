package com.example.tp_flashcard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
