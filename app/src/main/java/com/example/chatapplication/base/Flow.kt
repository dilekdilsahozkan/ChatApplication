package com.example.chatapplication.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> apiFlow(
    call:suspend () -> Response<T>?
): Flow<BaseResult<T & Any>?> = flow {
    emit(BaseResult.Loading)
    try {
        val c = call()
        c?.let {
            if (c.isSuccessful) {
                emit(c.body()?.let { it1 -> BaseResult.Success(it1) })
            } else {
                emit(BaseResult.Error(c.errorBody()?.string() ?: "An error"))
            }
        }
    } catch (e: Exception) {
        emit(BaseResult.Error(e.message ?: "An error"))
    }
}.flowOn(Dispatchers.IO)