package com.example.chatapplication.presentation.ui.recipe_list_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun ItemCustomText(text: String, style: TextStyle) {
    Text(text = text, style = style)
}