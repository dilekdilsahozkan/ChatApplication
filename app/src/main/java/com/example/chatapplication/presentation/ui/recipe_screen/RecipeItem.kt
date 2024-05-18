package com.example.chatapplication.presentation.ui.recipe_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import com.example.chatapplication.presentation.ui.recipe_screen.components.ItemListImage
import com.example.chatapplication.presentation.ui.recipe_screen.components.ItemListText

@Composable
fun RecipeItem(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemListImage(modifier = Modifier.padding(5.dp), image = R.drawable.baked_goods_1)
        ItemListText(modifier = Modifier.padding(12.dp))
        ItemListImage(modifier = Modifier.padding(5.dp), image = R.drawable.baked_goods_2)
        ItemListText(modifier = Modifier.padding(12.dp))
        ItemListImage(modifier = Modifier.padding(5.dp), image = R.drawable.baked_goods_3)
        ItemListText(modifier = Modifier.padding(12.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipeItemPreview() {
    RecipeItem()
}