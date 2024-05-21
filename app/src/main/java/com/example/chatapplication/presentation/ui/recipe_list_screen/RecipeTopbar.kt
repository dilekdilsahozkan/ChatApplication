package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.AllRecipe
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.data.remote.model.RecipeState
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import com.example.chatapplication.ui.theme.ChatApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recipe() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Recipes")
                },
                actions = {
                    IconButton(onClick = { /* Filtreleme ikonuna tıklandığında yapılacak işlemler */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_circle_svgrepo_com), // Filtreleme simgesi
                            contentDescription = "Filtreleme"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatApplicationTheme {

    }
}