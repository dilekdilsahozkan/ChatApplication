package com.example.chatapplication.presentation.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

// chat bot mesaj balonu
@Composable
fun ModelChatItem(response: String, isLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.CenterStart),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color("#F1F1F1".toColorInt()),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            if (isLoading) {
                GoogleLoadingIndicator()
            } else {
                Text(
                    text = response,
                    color = Color.Black
                )
            }
        }
    }
}