package com.example.chatapplication.data.remote.model

data class Recipe (
    val recipeId: Int?= null,
    var name: String?= null,
    var imageUrl: String?= null,
    var makingAmount: String?= null,
    var totalTime: String?= null
)

data class AllRecipe(
    val recipe: List<Recipe>? = null
)