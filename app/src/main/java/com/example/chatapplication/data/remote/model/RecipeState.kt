package com.example.chatapplication.data.remote.model

data class RecipeState (
    val isLoading: Boolean = false,
    val characters: List<AllRecipe?> = emptyList(),
    val error: String = ""
)