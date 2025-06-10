package com.example.tp_flashcard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tp_flashcard.ui.view.FlashcardScreen
import com.example.tp_flashcard.ui.view.HomeScreen
import com.example.tp_flashcard.viewmodel.HomeViewModel

@Composable
fun AppNavHost(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                homeViewModel = homeViewModel,
                onCategoryClick = { category ->
                    navController.navigate("flashcard/$category")
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
