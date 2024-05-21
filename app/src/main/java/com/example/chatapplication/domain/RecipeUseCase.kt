package com.example.chatapplication.domain

import com.example.chatapplication.base.BaseResult
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.data.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {

    fun getRecipe(): Flow<BaseResult<Recipe>> {
        return flow {
            val value = recipeRepository.getRecipe()

            if (value.isSuccessful && value.code() == 200) {
                emit(
                    BaseResult.Success(
                        value.body() ?: Recipe()
                    )
                )
            }
        }
    }
    fun getRecipeDetail(id:Int): Flow<BaseResult<RecipeDetail>> {
        return flow {
            val value = recipeRepository.getRecipeDetail(id)

            if (value.isSuccessful && value.code() == 200) {
                emit(
                    BaseResult.Success(
                        value.body() ?: RecipeDetail()
                    )
                )
            }
        }
    }
}