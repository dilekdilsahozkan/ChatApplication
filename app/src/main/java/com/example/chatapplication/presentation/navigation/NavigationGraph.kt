package com.example.chatapplication.presentation.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.chatapplication.presentation.ui.chat.ChatScreen
import com.example.chatapplication.presentation.ui.recipe_detail.RecipeDetailScreen
import com.example.chatapplication.presentation.ui.recipe_list_screen.RecipeScreen
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Destination.CHAT,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>,
    viewModel: RecipeViewModel,
) {

    val currentNavigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavigationBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Destination.CHAT
        ) {
            ChatScreen(navController, imagePicker, uriState)
        }

        composable(
            route = Destination.RECIPE
        ) {
            RecipeScreen(navController)
        }

        composable(
            route = Destination.RECIPE_DETAIL + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            RecipeDetailScreen(navController, backStackEntry.arguments?.getInt("recipeId"))
        }
    }
}

