package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.ChatApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar() {

    TopAppBar(title = { Text("Recipes") },
        actions = {
            IconButton(onClick = { /* Filtreleme ikonuna tıklandığında yapılacak işlemler */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "Filtreleme"
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatApplicationTheme {

    }
}