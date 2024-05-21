package com.example.chatapplication.base

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ItemCustomText(
         text: String,
         style: TextStyle,
         modifier: Modifier) {

    Text(text = text, style = style)
}