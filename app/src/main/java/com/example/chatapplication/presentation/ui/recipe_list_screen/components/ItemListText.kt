package com.example.chatapplication.presentation.ui.recipe_list_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.navigation.Destination
import com.example.chatapplication.ui.theme.customRed
import com.example.chatapplication.ui.theme.itemText
import com.example.chatapplication.ui.theme.semibold

@Composable
fun ItemListText(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ItemCustomText(
            text = recipe.name.toString(),
            style = itemText(fontFamily = semibold, fontSize = 16.sp),
            modifier = Modifier.clickable {
                navController.navigate(Destination.RECIPE_DETAIL) {
                }
            }
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
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