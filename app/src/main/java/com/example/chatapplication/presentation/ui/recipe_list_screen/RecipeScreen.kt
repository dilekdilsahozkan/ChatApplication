package com.example.chatapplication.presentation.ui.recipe_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val uiState by viewModel.recipeState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            RecipeTopBar()
            Spacer(modifier = Modifier.height(12.dp))
        }
    )
    {
        if (uiState is ViewState.Success) {
            val recipes: List<Recipe> =
                (uiState as ViewState.Success<AllRecipe>).data.items ?: emptyList()
            LazyColumn {
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
