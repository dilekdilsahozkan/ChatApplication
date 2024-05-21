package com.example.chatapplication.presentation.ui.chat

import android.graphics.Bitmap
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColorInt
import com.example.chatapplication.R
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.base.ChatState
import com.example.chatapplication.data.ChatUiEvent
import com.example.chatapplication.presentation.viewmodel.ChatViewModel


//Chat ekranı ve mesaj balonları.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    navController: NavController,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val chatViewModel = viewModel<ChatViewModel>()
    val chatState = chatViewModel.chatState.collectAsState().value
    val isLoading = chatViewModel.isLoading.collectAsState().value
    val bitmap = getBitmap(uriState)

    Scaffold(
        topBar = { ChatTopAppBar(navController) },
        bottomBar = {
            ChatInputBar(
                bitmap,
                imagePicker,
                uriState,
                chatState,
                chatViewModel,
                onImageClear = { uriState.value = "" }
            )
        }
    ) { innerPadding ->
        ChatBackground {
            LaunchedEffect(chatState.chatList) {
                if (chatState.chatList.isNotEmpty()) {
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
                        UserChatItem(prompt = chat.prompt, bitmap = chat.bitmap)
                    } else {
                        ModelChatItem(
                            response = chat.prompt,
                            isLoading = isLoading && chatState.chatList.size - 1 == index
                        )
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

@Composable
fun ChatInputBar(
    bitmap: Bitmap? = null,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>,
    chatState: ChatState,
    chatViewModel: ChatViewModel,
    onImageClear: () -> Unit
) {
    ProvideTextStyle(TextStyle(color = Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
        ) {
            bitmap?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 10.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentDescription = "picked image",
                        contentScale = ContentScale.Crop,
                        bitmap = it.asImageBitmap()
                    )
                    IconButton(
                        onClick = onImageClear,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Clear Image",
                            tint = Color.White
                        )
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                value = chatState.prompt,
                onValueChange = { chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color("#F9D8D8".toColorInt()),
                    unfocusedIndicatorColor = Color("#F9D8D8".toColorInt()),
                    focusedContainerColor = Color("#F9D8D8".toColorInt()),
                    focusedIndicatorColor = Color("#F9D8D8".toColorInt()),
                    disabledIndicatorColor = Color(
                        "#F9D8D8".toColorInt()
                    )
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
                        uriState.update { "" }
                    }
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            imagePicker.launch(
                                PickVisualMediaRequest
                                    .Builder()
                                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    .build()
                            )
                        },
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
                        onClick = {
                            chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
                            uriState.update { "" }
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
                }
            )
        }
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

// kullanıcı mesajı
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.CenterEnd)
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color("#F9D8D8".toColorInt()),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                bitmap?.let {
                    Image(
                        modifier = Modifier
                            .width(160.dp)
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        bitmap = it.asImageBitmap()
                    )
                }
                if (prompt.isNotEmpty()) {
                    Text(
                        text = prompt,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

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

@Composable
fun GoogleLoadingIndicator(
    modifier: Modifier = Modifier,
    dotSize: Dp = 5.dp,
    dotColor: Color = Color.Gray,
    animationDelay: Int = 200
) {
    val infiniteTransition = rememberInfiniteTransition()

    val dotScaleValues = (0..2).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(index * animationDelay)
            )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dotScaleValues.forEachIndexed { index, scale ->
            Box(
                modifier = Modifier
                    .size(dotSize * scale.value)
                    .background(dotColor, CircleShape)
            )
            if (index < 2) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
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
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen(
        navController = rememberNavController(),
        imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {},
        uriState = MutableStateFlow("")
    )
}
