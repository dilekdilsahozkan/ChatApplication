package com.example.chatapplication.data
import android.graphics.Bitmap
import com.example.chatapplication.data.Chat

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap?= null
)
