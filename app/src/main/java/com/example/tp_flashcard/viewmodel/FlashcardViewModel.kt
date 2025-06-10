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
 * @property currentCategory The category of the flashcards being reviewed.
 * @property flashcards The list of flashcards for the current session.
 * @property currentIndex The index of the currently displayed flashcard.
 * @property isFlipped Indicates whether the current flashcard is flipped to show the answer.
 * @property isReviewFinished Indicates whether the review session is complete.
 */
data class FlashcardUiState(
    val flashcards: List<FlashCard> = emptyList(),
    val currentIndex: Int = 0,
    val isReviewFinished: Boolean = false,
    val knownCardsCount: Int = 0
) {
    val currentCard: FlashCard?
        get() = flashcards.getOrNull(currentIndex)

    val progressText: String
        get() = if (flashcards.isNotEmpty() && !isReviewFinished) "${currentIndex + 1} / ${flashcards.size}" else ""

    val isLastCard: Boolean
        get() = if (flashcards.isEmpty()) true else currentIndex == flashcards.size - 1
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
     * Moves to the previous card in the list. This action resets the `isReviewFinished` flag if the user was at the end of the session.
     */
    fun onPreviousCard() {
        _uiState.update { currentState ->
            val prevIndex = if (currentState.currentIndex > 0) {
                currentState.currentIndex - 1
            } else {
                0
            }
            currentState.copy(
                currentIndex = prevIndex,
                isReviewFinished = false
            )
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
