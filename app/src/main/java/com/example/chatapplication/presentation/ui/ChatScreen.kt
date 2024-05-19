package com.example.chatapplication.presentation.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.chatapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf("") }

    data class Message(val text: String, val isSent: Boolean)

    val messages = remember {
        mutableStateListOf(
            Message("Hi!", false),
            Message("Are you here ?", true),
            Message("Please text me", false),
            Message("Ok, let me think", true)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = { Text(text = "Chat", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { /* Handle navigation drawer */ },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color("#F9D8D8".toColorInt()),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Menu",
                            tint = Color("#E23E3E".toColorInt())
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Handle search */ },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color("#F9D8D8".toColorInt()),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Search",
                            tint = Color("#E23E3E".toColorInt())
                        )
                    }
                },
            )
        },
        bottomBar = {
            ProvideTextStyle(TextStyle(color = Color.Black)) {
                TextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(),
                    value = messageText,
                    onValueChange = { messageText = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color("#F9D8D8".toColorInt()),
                        focusedContainerColor = Color("#F9D8D8".toColorInt()),
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(onSend = {
                        if (messageText.isNotBlank()) {
                            messages.add(Message(messageText, true))
                            messageText = ""
                        }
                    }),
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                // Handle camera/gallery action here
                            },
                            modifier = Modifier
                                .background(color = Color("#F9D8D8".toColorInt()))
                                .size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = "Gallery",
                                tint = Color("#E23E3E".toColorInt()),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (messageText.isNotBlank()) {
                                    messages.add(Message(messageText, true))
                                    messageText = ""
                                }
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = Color("#F9D8D8".toColorInt()))
                                .size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.doubleright),
                                contentDescription = "Send",
                                tint = Color("#E23E3E".toColorInt())
                            )
                        }
                    },
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bgchat),
                contentDescription = "Chat Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 75.dp)
            ) {
                items(messages) { message -> // Specify the type of 'message' as 'Message'
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = if (message.isSent) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Text(
                            text = message.text,
                            modifier = Modifier
                                .background(
                                    color = if (message.isSent) Color("#F9D8D8".toColorInt()) else Color("#F1F1F1".toColorInt()),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}
