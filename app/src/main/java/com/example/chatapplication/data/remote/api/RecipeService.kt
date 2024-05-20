package com.example.chatapplication.data.remote.api

import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

    @GET("/recipes")
    suspend fun recipe(): Response<AllRecipe>

    @GET("/recipes/{id}")
    suspend fun recipeDetail(
        @Path("id") movieId: String,
        ): Response<RecipeDetail>

}