package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tp_flashcard.FlashcardApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(flashcardApplication().repository)
        }

        initializer {
            FlashcardViewModel(
                this.createSavedStateHandle(),
                flashcardApplication().repository
            )
        }
    }
}

fun CreationExtras.flashcardApplication(): FlashcardApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlashcardApplication)
