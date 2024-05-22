package com.example.chatapplication.data

import android.graphics.Bitmap
import com.example.chatapplication.BuildConfig
import com.example.chatapplication.data.remote.model.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    private val api_key = "AIzaSyCuI7YQJEZJznAd1gWu6hikrCA6tnu4Tlc"

    suspend fun getResponse(prompt: String) :Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )
        try{
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            return Chat(
                prompt= response.text ?:"error",
                bitmap = null,
                isFromUser = false
            )
        }

        catch (e:Exception){
            return Chat(
                prompt= e.message?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

    suspend fun getResponse(prompt: String, bitmap: Bitmap) : Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = api_key
        )
        try{
            val inputContent= content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(inputContent)
            }
            return Chat(
                prompt= response.text ?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
        catch (e:Exception){
            return Chat(
                prompt= e.message?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

}