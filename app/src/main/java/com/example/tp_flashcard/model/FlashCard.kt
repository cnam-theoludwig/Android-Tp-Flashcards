package com.example.tp_flashcard.model

/**
 * Represents a flashcard with a question and its answer.
 *
 * @property id Unique identifier for the card.
 * @property categoryId Identifier for the category this card belongs to.
 * @property question Text of the question shown to the user.
 * @property answer Text of the answer revealed after flipping the card.
 */
data class FlashCard(
    val id: String,
    val categoryId: String,
    val question: String,
    val answer: String
)

/**
 * Represents a category grouping multiple flashcards.
 *
 * @property id Unique identifier for the category.
 * @property name Display name of the category.
 */
data class FlashCardCategory(
    val id: String,
    val name: String
)
