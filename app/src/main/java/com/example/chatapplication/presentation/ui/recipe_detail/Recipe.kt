package com.example.chatapplication.presentation.ui.recipe_detail

import androidx.annotation.DrawableRes
import com.example.chatapplication.R

data class Recipe(
    val title: String,
    val category: String,
    val cookingTime: String,
    val rating: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val instructions : String
)

data class Ingredient(@DrawableRes val image: Int, val title: String, val subtitle: String)

val strawberryCake = Recipe(
    title = "Strawberry Cake",
    category = "Desserts",
    cookingTime = "50 min",
    rating = "4,9",
    description = "This dessert is very tasty and not difficult to prepare. Also, you can replace strawberries with any other berry you like.",
    ingredients = listOf(
        Ingredient(R.drawable.flour, "Flour", "450 g"),
        Ingredient(R.drawable.eggs, "Eggs", "4"),
        Ingredient(R.drawable.juice, "Lemon juice", "150 g"),
        Ingredient(R.drawable.strawberry, "Strawberry", "200 g"),
        Ingredient(R.drawable.suggar, "Sugar", "1 cup"),
        Ingredient(R.drawable.mind, "Mint", "20 g"),
        Ingredient(R.drawable.vanilla, "Vanilla", "1/2 teaspoon"),
    ),
    instructions = "Preheat oven to 350 degrees F (175 degrees C). Grease and flour two 9-inch round cake pans.\n" +
            "In a large bowl, cream together the butter and sugar until light and fluffy. Beat in the eggs one at a time, then stir in the vanilla.\n" +
            "In a separate bowl, whisk together the flour, baking powder, and salt. Gradually add the dry ingredients to the wet ingredients, alternating with the milk, beginning and ending with the dry ingredients. Beat until just combined.\n" +
            "Fold in the strawberries. Divide the batter between the prepared cake pans and bake for 25-30 minutes, or until a toothpick inserted into the center comes out clean.\n" +
            "Let the cakes cool in the pans for 10 minutes before inverting them onto wire racks to cool completely.\n" +
            "Once the cakes are completely cool, frost them with your favorite frosting and decorate with additional strawberries."
)