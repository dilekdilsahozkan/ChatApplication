package com.example.chatapplication.presentation.ui.chat

import android.graphics.Bitmap
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
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
                    .padding(horizontal = 18.dp)
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
