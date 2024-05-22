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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapplication.R
import com.example.chatapplication.presentation.viewmodel.ChatViewModel
import com.example.chatapplication.ui.theme.MainColor
import com.example.chatapplication.ui.theme.SecondaryColor
import com.example.chatapplication.ui.theme.mediumFont
import com.example.chatapplication.ui.theme.regular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(navController: NavController, viewModel: ChatViewModel) {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        title = { Text(text = "Chat", fontFamily = mediumFont) },
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
                        MainColor,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Recipe Page",
                    tint = SecondaryColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = { viewModel.clearMessages() },
                modifier = Modifier
                    .background(
                        MainColor,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Notification",
                    tint = SecondaryColor
                )
            }
        },
    )
}