package com.example.tp_flashcard.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a flashcard with a question and its answer.
 *
 * @property id Unique identifier for the card.
 * @property categoryId Identifier for the category this card belongs to.
 * @property question Text of the question shown to the user.
 * @property answer Text of the answer revealed after flipping the card.
 */
@Entity(tableName = "flashcards")
data class FlashCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
@Entity(tableName = "categories")
data class FlashCardCategory(
    @PrimaryKey
    val id: String,
    val name: String
)
