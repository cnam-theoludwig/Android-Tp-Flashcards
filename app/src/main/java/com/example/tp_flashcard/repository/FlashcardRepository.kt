package com.example.tp_flashcard.repository

import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.model.FlashCardCategory

/**
 * Singleton providing flashcard data.
 */
object FlashcardRepository {
    val categories: List<FlashCardCategory> = listOf(
        FlashCardCategory(id = "cat1", name = "Jetpack Compose"),
        FlashCardCategory(id = "cat2", name = "Culture générale"),
        FlashCardCategory(id = "cat3", name = "Pop Culture"),
        FlashCardCategory(id = "cat4", name = "Inventions et découvertes")
    )

    val flashcards: List<FlashCard> = listOf(
        // Jetpack Compose
        FlashCard(id = "fc1", categoryId = "cat1", question = "Qu'est-ce qu'un 'Composable' ?", answer = "Une fonction qui décrit une partie de votre interface utilisateur."),
        FlashCard(id = "fc2", categoryId = "cat1", question = "Comment créer une rangée (Row) ?", answer = "En appelant la fonction composable `Row`."),
        FlashCard(id = "fc3", categoryId = "cat1", question = "A quoi sert `State` dans Compose ?", answer = "A stocker une valeur qui, lorsqu'elle change, déclenche une recomposition des composables qui la lisent."),

        // Culture générale
        FlashCard(id = "fc4", categoryId = "cat2", question = "Quelle est la capitale de l'Australie ?", answer = "Canberra."),
        FlashCard(id = "fc5", categoryId = "cat2", question = "Qui a écrit \"Les Misérables\" ?", answer = "Victor Hugo."),
        FlashCard(id = "fc6", categoryId = "cat2", question = "Quel est le plus long fleuve du monde ?", answer = "L'Amazone."),

        // Pop Culture
        FlashCard(id = "fc7", categoryId = "cat3", question = "Qui est le réalisateur de la trilogie \"Le Seigneur des Anneaux\" ?", answer = "Peter Jackson."),
        FlashCard(id = "fc8", categoryId = "cat3", question = "Dans quel univers de fiction se trouve la planète Tatooine ?", answer = "Star Wars."),
        FlashCard(id = "fc9", categoryId = "cat3", question = "Quel artiste a sorti l'album \"Thriller\" ?", answer = "Michael Jackson."),

        // Inventions et découvertes
        FlashCard(id = "fc10", categoryId = "cat4", question = "Qui a inventé l'imprimerie ?", answer = "Johannes Gutenberg."),
        FlashCard(id = "fc11", categoryId = "cat4", question = "Qui a découvert la pénicilline ?", answer = "Alexander Fleming."),
        FlashCard(id = "fc12", categoryId = "cat4", question = "A qui attribue-t-on l'invention du téléphone ?", answer = "Alexander Graham Bell.")
    )
}
