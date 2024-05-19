package com.example.chatapplication.presentation.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.chatapplication.R
import kotlinx.coroutines.launch

//Chat ekranı ve mesaj balonları.
data class Message(val text: String, val isSent: Boolean)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            Message("Hi!", false),
            Message("Are you here ?", true),
            Message("Please text me", false),
            Message("Ok, let me think", true)
        )
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = { ChatTopAppBar() },
        bottomBar = {
            ChatInputBar(
                messageText,
                onValueChange = { messageText = it },
                onSend = {
                    if (it.isNotBlank()) {
                        messages.add(Message(it, true))
                        messageText = ""
                        scope.launch {
                            listState.animateScrollToItem(index = messages.size - 1)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        ChatBackground {
            //Mesaj balonları.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .imePadding(),
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.Bottom,
                state = listState
            ) {
                items(messages) { message ->
                    MessageItem(message)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

//chat ekranını üst çubuğu.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar() {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        title = { Text(text = "Chat", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {
            IconButton(
                onClick = {
                    /*Navigation Kodları Buraya gelecek*/
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

//chat ekranın altında bulunan mesaj giriş alanı.
@Composable
fun ChatInputBar(message: String, onValueChange: (String) -> Unit, onSend: (String) -> Unit) {
    ProvideTextStyle(TextStyle(color = Color.Black)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(16.dp)),
            value = message,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(

                unfocusedContainerColor = Color("#F9D8D8".toColorInt()),
                unfocusedIndicatorColor = Color("#F9D8D8".toColorInt()),
                focusedContainerColor = Color("#F9D8D8".toColorInt()),
                focusedIndicatorColor = Color("#F9D8D8".toColorInt()),
                disabledIndicatorColor = Color("#F9D8D8".toColorInt()),

                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(onSend = { onSend(message) }),
            leadingIcon = {
                IconButton(
                    onClick = { /* Kamera butonu click eventi içeriği */ },
                    modifier = Modifier
                        .background(color = Color("#F9D8D8".toColorInt()))
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera",
                        tint = Color("#E23E3E".toColorInt()),
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onSend(message) },
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

//chat ekranının arka planı.
@Composable
fun ChatBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bgchat),
            contentDescription = "Chat Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        content()
    }
}

//chat mesajları.
@Composable
fun MessageItem(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(if (message.isSent) Alignment.CenterEnd else Alignment.CenterStart),
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

//Preview Alanı
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}


