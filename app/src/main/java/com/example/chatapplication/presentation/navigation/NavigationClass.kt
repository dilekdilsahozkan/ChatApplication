package com.example.chatapplication.presentation.navigation

import androidx.navigation.NavHostController

object Destination {
    const val RECIPE = "recipe"
    const val RECIPE_DETAIL = "recipe_detail"
    const val CHAT = "chat"
}

class MortyNavigationActions(private val navController : NavHostController) {
    fun navigateToHome() {
        navController.navigate(Destination.CHAT) {
            popUpTo(Destination.CHAT) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateToRecipe() {
        navController.navigate(Destination.RECIPE)
    }

    fun navigateToRecipeDetail() {
        navController.navigate(Destination.RECIPE_DETAIL)
    }


}