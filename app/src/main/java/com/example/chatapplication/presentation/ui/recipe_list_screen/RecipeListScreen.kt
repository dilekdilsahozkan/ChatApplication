package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(viewModel: RecipeViewModel = hiltViewModel()) {
    val initialViewState: ViewState<AllRecipe> = ViewState.Idle()
   // val uiState = viewModel.recipeState.collectAsState(initial = initialViewState)

    val uiState by viewModel.recipeState.collectAsState(initial = ViewState.Idle())


    Column {
       RecipeTopBar()

       when (uiState) {
           is ViewState.Idle -> {
           }
           is ViewState.Loading -> {
               CircularProgressIndicator()
           }
           is ViewState.Success -> {
               val recipes: List<Recipe> = (uiState as ViewState.Success<AllRecipe>).data.recipe ?: emptyList()
               LazyColumn {
                   items(recipes) { recipe ->
                       RecipeListItem(recipe = recipe)
                   }
               }
           }
           is ViewState.Error -> {
               Text("Error: ${(uiState as ViewState.Error<AllRecipe>).message}")
           }
       }

   }
}
