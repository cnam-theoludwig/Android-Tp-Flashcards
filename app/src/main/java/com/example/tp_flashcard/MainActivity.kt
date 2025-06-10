package com.example.tp_flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tp_flashcard.ui.theme.TP_FlashcardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP_FlashcardTheme {
                AppNavHost()
            }
        }
    }
}
