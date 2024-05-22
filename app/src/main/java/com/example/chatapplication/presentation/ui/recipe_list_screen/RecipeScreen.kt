package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel

@Composable
fun RecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val uiState by viewModel.recipeState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getRecipe()
    }

    Scaffold(
        topBar = {
            RecipeTopBar(navController)
        }
    ) { paddingValues ->
        if (uiState is ViewState.Success) {
            val recipes: List<RecipeDetail> =
                (uiState as ViewState.Success<Recipe>).data.items ?: emptyList()
            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(recipes) { recipe ->
                    RecipeListItem(
                        recipe = recipe,
                        navController = navController,
                    )
                }
            }
        }
    }
}
