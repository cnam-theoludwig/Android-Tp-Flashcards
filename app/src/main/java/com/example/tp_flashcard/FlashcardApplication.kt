package com.example.tp_flashcard

import android.app.Application
import com.example.tp_flashcard.data.AppDatabase
import com.example.tp_flashcard.repository.FlashcardRepository

class FlashcardApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { FlashcardRepository.getInstance(database.flashcardDao()) }
}
