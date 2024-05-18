package com.example.chatapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.base.BaseResult
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.domain.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val recipeUseCase: RecipeUseCase) : ViewModel() {

    var _recipeState: MutableStateFlow<ViewState<AllRecipe>> = MutableStateFlow(ViewState.Idle())
    val recipeState: StateFlow<ViewState<AllRecipe>> = _recipeState

    var _detailState: MutableStateFlow<ViewState<RecipeDetail>> = MutableStateFlow(ViewState.Idle())
    val detailState: StateFlow<ViewState<RecipeDetail>> = _detailState

    fun getRecipe() {
        viewModelScope.launch {
            recipeUseCase.getRecipe()
                .onStart {
                    _recipeState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _recipeState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _recipeState.value = ViewState.Success(result.data)
                        }

                        is BaseResult.Error -> {
                            _recipeState.value = ViewState.Error()
                        }

                        else -> {}
                    }
                }
        }
    }

    fun getRecipeDetail(id: String) {
        viewModelScope.launch {
            recipeUseCase.getRecipeDetail(id)
                .onStart {
                    _recipeState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _detailState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _detailState.value = ViewState.Success(result.data)
                        }

                        is BaseResult.Error -> {
                            _detailState.value = ViewState.Error()
                        }

                        else -> {}
                    }
                }
        }
    }
}