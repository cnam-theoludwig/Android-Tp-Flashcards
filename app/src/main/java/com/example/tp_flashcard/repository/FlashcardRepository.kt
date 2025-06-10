package com.example.tp_flashcard.repository

import com.example.tp_flashcard.data.FlashcardDao
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.model.FlashCardCategory
import kotlinx.coroutines.flow.Flow

class FlashcardRepository private constructor(private val flashcardDao: FlashcardDao) {

    fun getAllCategories(): Flow<List<FlashCardCategory>> = flashcardDao.getAllCategories()

    fun getFlashcardsForCategory(categoryId: String): Flow<List<FlashCard>> =
        flashcardDao.getFlashcardsForCategory(categoryId)

    suspend fun prepopulateDatabase() {
        flashcardDao.insertCategories(initialCategories)
        flashcardDao.insertFlashcards(initialFlashcards)
    }

    companion object {
        @Volatile
        private var INSTANCE: FlashcardRepository? = null

        fun getInstance(flashcardDao: FlashcardDao): FlashcardRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = FlashcardRepository(flashcardDao)
                INSTANCE = instance
                instance
            }
        }

        private val initialCategories = listOf(
            FlashCardCategory(id = "cat1", name = "Jetpack Compose"),
            FlashCardCategory(id = "cat2", name = "Culture générale"),
            FlashCardCategory(id = "cat3", name = "Pop Culture"),
            FlashCardCategory(id = "cat4", name = "Inventions et découvertes")
        )

        private val initialFlashcards = listOf(
            // Jetpack Compose
            FlashCard(categoryId = "cat1", question = "Qu'est-ce qu'un 'Composable' ?", answer = "Une fonction qui décrit une partie de votre interface utilisateur."),
            FlashCard(categoryId = "cat1", question = "Comment créer une rangée (Row) ?", answer = "En appelant la fonction composable `Row`."),
            FlashCard(categoryId = "cat1", question = "A quoi sert `State` dans Compose ?", answer = "A stocker une valeur qui, lorsqu'elle change, déclenche une recomposition des composables qui la lisent."),

            // Culture générale
            FlashCard(categoryId = "cat2", question = "Quelle est la capitale de l'Australie ?", answer = "Canberra."),
            FlashCard(categoryId = "cat2", question = "Qui a écrit \"Les Misérables\" ?", answer = "Victor Hugo."),
            FlashCard(categoryId = "cat2", question = "Quel est le plus long fleuve du monde ?", answer = "L'Amazone."),

            // Pop Culture
            FlashCard(categoryId = "cat3", question = "Qui est le réalisateur de la trilogie \"Le Seigneur des Anneaux\" ?", answer = "Peter Jackson."),
            FlashCard(categoryId = "cat3", question = "Dans quel univers de fiction se trouve la planète Tatooine ?", answer = "Star Wars."),
            FlashCard(categoryId = "cat3", question = "Quel artiste a sorti l'album \"Thriller\" ?", answer = "Michael Jackson."),

            // Inventions et découvertes
            FlashCard(categoryId = "cat4", question = "Qui a inventé l'imprimerie ?", answer = "Johannes Gutenberg."),
            FlashCard(categoryId = "cat4", question = "Qui a découvert la pénicilline ?", answer = "Alexander Fleming."),
            FlashCard(categoryId = "cat4", question = "A qui attribue-t-on l'invention du téléphone ?", answer = "Alexander Graham Bell.")
        )
    }
}
