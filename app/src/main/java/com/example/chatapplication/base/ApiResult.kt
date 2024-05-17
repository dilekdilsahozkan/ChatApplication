package com.example.chatapplication.base

sealed class ApiResult <out T> {
    data class Success<out R>(val data: R?): ApiResult<R>()
    data class Error(val message: String): ApiResult<Nothing>()
    data object Loading: ApiResult<Nothing>()
}