package com.example.tp_flashcard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.model.FlashCardCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<FlashCardCategory>>

    @Query("SELECT * FROM flashcards WHERE categoryId = :categoryId")
    fun getFlashcardsForCategory(categoryId: String): Flow<List<FlashCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<FlashCardCategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(flashcards: List<FlashCard>)
}
