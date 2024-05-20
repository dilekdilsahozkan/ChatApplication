package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(recipeViewModel: RecipeViewModel = hiltViewModel()) {
    when (val recipeState = recipeViewModel.recipeState.collectAsState().value) {
        is ViewState.Idle -> {
        }
        is ViewState.Loading -> {
            CircularProgressIndicator()
        }
        is ViewState.Success -> {
            LazyColumn {
                items(recipeState.data.recipe!!) { recipe ->
                    RecipeListItem(recipe = recipe)
                }
            }
        }
        is ViewState.Error -> {
            Text("Error: ${recipeState.message}")
        }
    }
}