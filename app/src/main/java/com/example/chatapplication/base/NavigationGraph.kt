package com.example.chatapplication.base

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.presentation.ui.ChatScreen
import com.example.chatapplication.presentation.ui.RecipesDetail
import com.example.chatapplication.presentation.ui.recipe_list_screen.RecipeListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    //coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = Destination.CHAT,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>,
    navActions: MortyNavigationActions = remember(navController) {
        MortyNavigationActions(navController)
    }
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
            RecipeListScreen()
        }

        composable(
            route = Destination.RECIPE_DETAIL
        ) {

        }

    }
}