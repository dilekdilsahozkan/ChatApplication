package com.example.chatapplication.data

import android.graphics.Bitmap

sealed class ChatUiEvent {
    data class  UpdatePrompt(val newPrompt: String) : ChatUiEvent()
    data class UpdateWithBitmap(val newPrompt: String, val newBitmap: Bitmap) : ChatUiEvent()
    data class SendPrompt(
        val prompt: String,
        val bitmap: Bitmap?
    ) : ChatUiEvent()
}
