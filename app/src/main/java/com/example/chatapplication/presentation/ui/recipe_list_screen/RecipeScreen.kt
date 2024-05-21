package com.example.chatapplication.presentation.ui.recipe_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.chatapplication.R
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import com.example.chatapplication.ui.theme.semibold

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()) {

    val uiState by viewModel.recipeState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Recipes",
                        fontFamily = semibold)
                },
                actions = {
                    IconButton(onClick = { /* Chat */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chat), // Filtreleme simgesi
                            contentDescription = "Opens Chat Screen"
                        )
                    }
                }
            )
        }
    )
    {
        if (uiState is ViewState.Success) {
            val recipes: List<Recipe> = (uiState as ViewState.Success<AllRecipe>).data.items ?: emptyList()
            LazyColumn {
                items(recipes) { recipe ->
                    RecipeListItem(recipe = recipe, navController = navController)
                }
            }
        }

    }

}
