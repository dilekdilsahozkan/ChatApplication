package com.example.chatapplication.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.base.ChatState
import com.example.chatapplication.data.ChatData
import com.example.chatapplication.data.ChatUiEvent
import com.example.chatapplication.data.remote.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()
    val isLoading = MutableStateFlow(false)

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)
                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(
                        prompt = event.newPrompt
                    )
                }
            }

            is ChatUiEvent.UpdateWithBitmap -> {
                _chatState.update {
                    it.copy(
                        bitmap = event.newBitmap,
                        prompt = event.newPrompt
                    )
                }
            }

        }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt, bitmap, isFromUser = true))
                },
                prompt = "",
                bitmap = null

            )
        }

    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            isLoading.value = true
            val chat = Chat("", null, isFromUser = false)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
            val response = ChatData.getReponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        this[0] = response
                    }
                )
            }
            isLoading.value = false
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            isLoading.value = true
            val chat = Chat("", null, isFromUser = false)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
            val response = ChatData.getReponse(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        this[0] = response
                    }
                )
            }
            isLoading.value = false
        }
    }
}