package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_flashcard.model.FlashCardCategory
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class HomeUiState(val categories: List<FlashCardCategory> = emptyList())

class HomeViewModel(private val repository: FlashcardRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllCategories().collect { categories ->
                _uiState.value = HomeUiState(categories = categories)
            }
        }
    }
}
