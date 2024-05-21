package com.example.chatapplication.presentation.ui.recipe_list_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.ui.recipe_list_screen.components.ItemListImage
import com.example.chatapplication.presentation.ui.recipe_list_screen.components.ItemListText

@Composable
fun RecipeListItem(recipe: Recipe) {
    recipe.imageUrl?.let { ItemListImage(image = it) }
    ItemListText()
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipeItemPreview() {
}