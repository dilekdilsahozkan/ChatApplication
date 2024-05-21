package com.example.chatapplication.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    val api_key="AIzaSyCuI7YQJEZJznAd1gWu6hikrCA6tnu4Tlc"

    suspend fun getReponse(prompt: String) : com.example.chatapplication.data.Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )
        try{
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            return com.example.chatapplication.data.Chat(
                prompt= response.text ?:"error",
                bitmap = null,
                isFromUser = false
            )
        }

        catch (e:Exception){
            return com.example.chatapplication.data.Chat(
                prompt= e.message?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

    suspend fun getReponse(prompt: String, bitmap: Bitmap) : com.example.chatapplication.data.Chat {
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
            return com.example.chatapplication.data.Chat(
                prompt= response.text ?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
        catch (e:Exception){
            return com.example.chatapplication.data.Chat(
                prompt= e.message?:"error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

}