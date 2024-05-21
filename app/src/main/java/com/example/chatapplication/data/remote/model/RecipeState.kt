package com.example.chatapplication.data.remote.model

data class RecipeState (
    val isLoading: Boolean = false,
    val recipe: List<Recipe?> = emptyList(),
    val error: String = ""
)