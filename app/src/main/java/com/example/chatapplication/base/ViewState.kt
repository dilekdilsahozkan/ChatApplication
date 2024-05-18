package com.example.chatapplication.base

sealed class ViewState<T> {
    data class Success<T>(val data:T): ViewState<T>()
    data class Error<T>(val error: T? = null, val message: String? = null ): ViewState<T>()
    class Idle<T> : ViewState<T>()
    class Loading<T> : ViewState<T>()
}