package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tp_flashcard.model.FlashCardCategory
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<FlashCardCategory>>(emptyList())

    val categories: StateFlow<List<FlashCardCategory>> = _categories.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = FlashcardRepository.categories
    }
}
