package com.example.chatapplication.data.remote.model

data class RecipeDetail (
    val recipeId: Int?= null,
    var name: String?= null,
    var directions: List<String>?= null,
    var ingredients: List<String>? = null,
    var imageUrl: String?= null,
    var makingAmount: String?= null,
    var totalTime: String?= null
)