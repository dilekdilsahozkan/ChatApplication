package com.example.chatapplication.data.repository

import com.example.chatapplication.data.remote.api.RecipeService
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import retrofit2.Response
import javax.inject.Inject

class RecipeRepository  @Inject constructor(private val service: RecipeService) {
    suspend fun getRecipe() : Response<Recipe> = service.recipe()
    suspend fun getRecipeDetail(id: Int) : Response<RecipeDetail> = service.recipeDetail(id)
}