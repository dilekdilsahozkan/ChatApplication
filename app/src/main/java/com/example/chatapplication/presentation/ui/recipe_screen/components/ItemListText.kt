package com.example.chatapplication.presentation.ui.recipe_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapplication.ui.theme.customRed
import com.example.chatapplication.ui.theme.itemText
import com.example.chatapplication.ui.theme.semibold

@Composable
fun ItemListText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ItemCustomText(
            text = "How to make french toast",
            style = itemText(fontFamily = semibold, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ItemCustomText(
                text = "Making amount: ",
                style = itemText(color = customRed, fontSize = 12.sp)
            )
            ItemCustomText(
                text = "12 pieces",
                style = itemText(color = Color.Black, fontSize = 12.sp)
            )
            ItemCustomText(
                text = "Making time: ",
                style = itemText(color = customRed, fontSize = 12.sp)
            )
            ItemCustomText(
                text = "12 minutes",
                style = itemText(color = Color.Black, fontSize = 12.sp)
            )
        }
    }
}