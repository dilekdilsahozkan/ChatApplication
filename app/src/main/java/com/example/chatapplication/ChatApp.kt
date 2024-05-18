package com.example.chatapplication

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.v("PatikaApp","onCreate")
    }
}