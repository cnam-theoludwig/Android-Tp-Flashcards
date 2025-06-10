package com.example.tp_flashcard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tp_flashcard.ui.view.FlashcardScreen
import com.example.tp_flashcard.ui.view.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate("flashcard/$categoryId")
                }
            )
        }
        composable(
            route = "flashcard/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) {
            FlashcardScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
