package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.model.FlashCardCategory
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Represents the UI state for the flashcard review screen.
 *
 *
 * @property flashcards The list of flashcards for the current session.
 * @property currentIndex The index of the currently displayed flashcard.
 * @property isReviewFinished Indicates whether the review session is finished.
 * @property knownCardsCount The count of cards the user has marked as known.
 */
data class FlashcardUiState(
    val flashcards: List<FlashCard> = emptyList(),
    val currentIndex: Int = 0,
    val isReviewFinished: Boolean = false,
    val knownCardsCount: Int = 0
) {
    val progressText: String
        get() = if (flashcards.isNotEmpty() && !isReviewFinished) "${currentIndex + 1} / ${flashcards.size}" else ""

    val isLastCard: Boolean
        get() = flashcards.isEmpty() || currentIndex == flashcards.size - 1
}

class FlashcardViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: FlashcardRepository
) : ViewModel() {

    private val categoryId: String = savedStateHandle.get<String>("categoryId")!!

    private val _uiState = MutableStateFlow(FlashcardUiState())
    val uiState: StateFlow<FlashcardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFlashcardsForCategory(categoryId).collect { cards ->
                _uiState.update { it.copy(flashcards = cards, isReviewFinished = cards.isEmpty()) }
            }
        }
    }

    /**
     * Moves to the next card in the list. If the current card is the last one, it marks the review session as finished.
     */
    fun onAnswer(known: Boolean) {
        _uiState.update { currentState ->
            val newKnownCount = if (known) currentState.knownCardsCount + 1 else currentState.knownCardsCount

            if (currentState.isLastCard) {
                currentState.copy(isReviewFinished = true, knownCardsCount = newKnownCount)
            } else {
                val nextIndex = currentState.currentIndex + 1
                currentState.copy(currentIndex = nextIndex, knownCardsCount = newKnownCount)
            }
        }
    }

    /**
     * Restarts the review session from the beginning.
     */
    fun onRestart() {
        _uiState.update {
            it.copy(
                currentIndex = 0,
                isReviewFinished = false,
                knownCardsCount = 0
            )
        }
    }
}
