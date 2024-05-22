package com.example.chatapplication.presentation.ui.chat

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.chatapplication.R
import com.example.chatapplication.base.ChatState
import com.example.chatapplication.data.ChatUiEvent
import com.example.chatapplication.presentation.viewmodel.ChatViewModel
import com.example.chatapplication.ui.theme.Black
import com.example.chatapplication.ui.theme.MainColor
import com.example.chatapplication.ui.theme.SecondaryColor
import com.example.chatapplication.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun ChatInputBar(
    bitmap: Bitmap? = null,
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
    uriState: MutableStateFlow<String>,
    chatState: ChatState,
    chatViewModel: ChatViewModel,
    onImageClear: () -> Unit,
) {
    ProvideTextStyle(TextStyle(Black)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                verticalAlignment = Alignment.Bottom
            ) {
                bitmap?.let {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
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
                                tint = White
                            )
                        }
                    }
                }
                TextField(
                    modifier = Modifier
                        .systemBarsPadding()
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    value = chatState.prompt,
                onValueChange = { chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MainColor,
                    unfocusedIndicatorColor = MainColor,
                    focusedContainerColor = MainColor,
                    focusedIndicatorColor = MainColor,
                    disabledIndicatorColor = MainColor
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
                            .background(MainColor)
                            .size(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Camera",
                            tint = SecondaryColor,
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
                            .background(MainColor)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.doubleright),
                            contentDescription = "Send",
                            tint = SecondaryColor
                        )
                    }
                }
            )
        }
    }
}}