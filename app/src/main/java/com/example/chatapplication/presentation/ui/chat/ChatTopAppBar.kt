package com.example.chatapplication.presentation.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.chatapplication.ui.theme.regular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(navController: NavController) {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        title = { Text(text = "Chat", fontFamily = regular) },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {
            IconButton(
                onClick = {

                    navController.navigate("recipe")
                },
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(48.dp)
                    .background(
                        Color("#F9D8D8".toColorInt()),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Recipe Page",
                    tint = Color("#E23E3E".toColorInt())
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /* Buton Click iceriği */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color("#F9D8D8".toColorInt()),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = Color("#E23E3E".toColorInt())
                )
            }
        },
    )
}