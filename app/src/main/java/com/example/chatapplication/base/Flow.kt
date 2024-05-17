package com.example.chatapplication.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> apiFlow(
    call:suspend () -> Response<T>?
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading)
    try {
        val c = call()
        c?.let {
            if (c.isSuccessful) {
                emit(ApiResult.Success(c.body()))
            } else {
                emit(ApiResult.Error(c.errorBody()?.string() ?: "An error bidi bidi"))
            }
        }
    } catch (e: Exception) {
        emit(ApiResult.Error(e.message ?: "An error bidi bidi"))
    }
}.flowOn(Dispatchers.IO)