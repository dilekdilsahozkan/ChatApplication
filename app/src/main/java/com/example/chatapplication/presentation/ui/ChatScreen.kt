import androidx.compose.foundation.layout.*
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen() {
    var isTimeVisible by remember { mutableStateOf(true) }
    var messageText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    data class Message(val text: String, val time: String)

    val messages = remember {
        mutableStateListOf(
            Message("Hi!", "22:45"),
            Message("Are you here ?", "23:10"),
            Message("Please text me ", "00:30")
        )
    }


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() },//metin alanı dışında tıklandığında
            // klavye kapansın
            verticalArrangement = Arrangement.Bottom
        ) {
            messages.forEach { message ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(150.dp)
                        .height(30.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
                            text = message.text,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 6.dp, bottom = 4.dp),
                            text = message.time,
                            color = Color.LightGray,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                // Message text box
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(onSend = {
                        if (messageText.isNotBlank()) {
                            messages.add(Message(messageText, currentTime))
                            messageText = ""
                        }
                    }),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp)),
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages.add(Message(messageText, currentTime))
                            messageText = ""
                        }
                    },
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(color = Color(0xFF5F5252))
                        .size(width = 50.dp, height = 50.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                    }
                }

            }
        }
    }
}

