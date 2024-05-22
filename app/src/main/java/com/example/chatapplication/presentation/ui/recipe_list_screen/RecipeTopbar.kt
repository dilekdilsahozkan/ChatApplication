package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import com.example.chatapplication.ui.theme.mediumFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(navController: NavController) {

    TopAppBar(
        title = { Text(text = "Recipes", fontFamily =  mediumFont) },
        actions = {
            IconButton(onClick = { navController.navigate("chat") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "Chat"
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