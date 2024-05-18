package com.example.chatapplication.base

import android.graphics.Bitmap
import com.example.chatapplication.data.remote.model.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)