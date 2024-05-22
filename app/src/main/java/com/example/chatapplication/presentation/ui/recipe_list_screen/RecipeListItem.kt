package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chatapplication.base.ItemCustomText
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.ui.theme.customRed
import com.example.chatapplication.ui.theme.itemText
import com.example.chatapplication.ui.theme.semibold

@Composable
fun RecipeListItem(
    recipe: RecipeDetail,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable {
                navController.navigate("recipe_detail/${recipe.recipeId}") },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp, 300.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {

            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            ItemCustomText(
                text = recipe.name.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                style = itemText(fontFamily = semibold, fontSize = 16.sp),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ItemCustomText(
                    text = "Making amount: ",
                    style = itemText(color = customRed, fontSize = 12.sp),
                    modifier = Modifier
                )
                ItemCustomText(
                    text =
                    if (recipe.makingAmount.isNullOrBlank()) "-" else recipe.makingAmount.toString(),
                    style = itemText(color = Color.Black, fontSize = 12.sp),
                    modifier = Modifier
                )
                ItemCustomText(
                    text = "Making time: ",
                    style = itemText(color = customRed, fontSize = 12.sp),
                    modifier = Modifier
                )
                ItemCustomText(
                    text =
                    if (recipe.totalTime.isNullOrBlank()) "-" else recipe.totalTime.toString(),
                    style = itemText(color = Color.Black, fontSize = 12.sp),
                    modifier = Modifier
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipeItemPreview() {
}