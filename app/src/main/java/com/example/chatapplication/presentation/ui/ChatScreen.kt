package com.example.chatapplication.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColorInt
import com.example.chatapplication.R
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapplication.data.ChatState
import com.example.chatapplication.data.ChatUiEvent
import com.example.chatapplication.presentation.viewmodel.ChatViewModel
import com.google.android.gms.cast.framework.media.ImagePicker


//Chat ekranı ve mesaj balonları.
@OptIn(ExperimentalMaterial3Api::class)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavController, imagePicker: ActivityResultLauncher<PickVisualMediaRequest>, uriState: MutableStateFlow<String>) {




    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val chatViewModel = viewModel<ChatViewModel>()
    val chatState = chatViewModel.chatState.collectAsState().value

    val bitmap = getBitmap(uriState)

    Scaffold(
        topBar = { ChatTopAppBar(navController) },
        bottomBar = {
            ChatInputBar(bitmap,imagePicker,uriState,chatState,chatViewModel)
        }
    ) { innerPadding ->
        ChatBackground {
            //Mesaj balonları.
            LaunchedEffect(chatState.chatList.size) {
                if (chatState.chatList.size > 0){
                listState.animateScrollToItem(chatState.chatList.size - 1)
                    }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .clickable { focusManager.clearFocus() }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .imePadding(),
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.Bottom,
                state = listState
            ) {
                itemsIndexed(chatState.chatList.reversed()) { index, chat ->
                    if (chat.isFromUser) {
                        UserChatItem(
                            prompt = chat.prompt, bitmap = chat.bitmap
                        )
                    } else {
                        ModelChatItem(response = chat.prompt)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

//chat ekranını üst çubuğu.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(navController: NavController) {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        title = { Text(text = "Chat", fontWeight = FontWeight.Bold) },
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

//chat ekranın altında bulunan mesaj giriş alanı.
//@Composable
//fun ChatInputBar(message: String, onValueChange: (String) -> Unit, onSend: (String) -> Unit) {
//    ProvideTextStyle(TextStyle(color = Color.Black)) {
//        TextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
//                .clip(RoundedCornerShape(16.dp)),
//            value = message,
//            onValueChange = onValueChange,
//            colors = TextFieldDefaults.colors(
//
//                unfocusedContainerColor = Color("#F9D8D8".toColorInt()),
//                unfocusedIndicatorColor = Color("#F9D8D8".toColorInt()),
//                focusedContainerColor = Color("#F9D8D8".toColorInt()),
//                focusedIndicatorColor = Color("#F9D8D8".toColorInt()),
//                disabledIndicatorColor = Color("#F9D8D8".toColorInt()),
//
//                ),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
//            keyboardActions = KeyboardActions(
//                onSend = { onSend(message) }
//            ),
//            leadingIcon = {
//                IconButton(
//                    onClick = { /* Kamera butonu click eventi içeriği */ },
//                    modifier = Modifier
//                        .background(color = Color("#F9D8D8".toColorInt()))
//                        .size(30.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.camera),
//                        contentDescription = "Camera",
//                        tint = Color("#E23E3E".toColorInt()),
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            },
//            trailingIcon = {
//                IconButton(
//                    onClick = { onSend(message) },
//
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .background(color = Color("#F9D8D8".toColorInt()))
//                        .size(24.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.doubleright),
//                        contentDescription = "Send",
//                        tint = Color("#E23E3E".toColorInt())
//                    )
//                }
//            },
//        )
//    }
//}
@Composable
fun ChatInputBar(bitmap: Bitmap? = null, imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
                 uriState: MutableStateFlow<String>,chatState: ChatState,chatViewModel: ChatViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            bitmap?.let {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 2.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentDescription = "picked image",
                    contentScale = ContentScale.Crop,
                    bitmap = it.asImageBitmap()
                )
            }

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        imagePicker.launch(
                            PickVisualMediaRequest
                                .Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                .build()
                        )
                    },
                imageVector = Icons.Rounded.AddPhotoAlternate,
                contentDescription = "Add Photo",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            modifier = Modifier
                .weight(1f),
            value = chatState.prompt,
            onValueChange = {
                chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it))
            },
            placeholder = {
                Text(text = "Type a prompt")
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
                    uriState.update { "" }
                },
            imageVector = Icons.Rounded.Send,
            contentDescription = "Send prompt",
            tint = MaterialTheme.colorScheme.primary
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
//@Composable
//fun MessageItem(message: Message) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentSize(if (message.isSent) Alignment.CenterEnd else Alignment.CenterStart),
//        contentAlignment = if (message.isSent) Alignment.CenterEnd else Alignment.CenterStart
//    ) {
//        Text(
//            text = message.text,
//            modifier = Modifier
//                .background(
//                    color = if (message.isSent) Color("#F9D8D8".toColorInt()) else Color("#F1F1F1".toColorInt()),
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(12.dp),
//            color = Color.Black
//        )
//    }
//}

// kullanıcı mesajı
@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Message bubble
        Box(
            modifier = Modifier
                .background(
                    color = Color("#F9D8D8".toColorInt()),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = prompt,
                color = Color.Black
            )
        }

        // Avatar or icon
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(CircleShape)
                .background(Color("#F9D8D8".toColorInt()))
                .size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            bitmap?.let {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    bitmap = it.asImageBitmap()
                )
            } ?: Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                tint = Color.Gray
            )
        }
    }
}
// chat bot mesaj balonu

@Composable
fun ModelChatItem(response :String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize( Alignment.CenterStart),
        contentAlignment = Alignment.CenterStart
    ){
        Text(
            text = response,
            modifier = Modifier
                .background(
                    color = Color("#F1F1F1".toColorInt()),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp),
            color = Color.Black
        )
    }
}


@Composable
private fun getBitmap(uriState: MutableStateFlow<String>): Bitmap? {
    val uri = uriState.collectAsState().value

    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(coil.size.Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Success) {
        return imageState.result.drawable.toBitmap()
    }

    return null
}

//Preview Alanı
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun PreviewChatScreen() {
//    MyApp()
//}
