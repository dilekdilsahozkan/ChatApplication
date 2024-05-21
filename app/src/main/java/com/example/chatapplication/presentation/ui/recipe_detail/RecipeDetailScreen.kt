package com.example.chatapplication.presentation.ui.recipe_detail

import androidx.compose.runtime.Composable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.chatapplication.R
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import com.example.chatapplication.ui.theme.Gray
import com.example.chatapplication.ui.theme.LightGray
import com.example.chatapplication.ui.theme.Pink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.max
import kotlin.math.min

val small: CornerBasedShape = RoundedCornerShape(4.dp)
val medium: CornerBasedShape = RoundedCornerShape(4.dp)
val large: CornerBasedShape = RoundedCornerShape(0.dp)

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeDetail: RecipeDetail,
    viewModel: RecipeViewModel) {

    val scrollState = rememberLazyListState()

   /* LaunchedEffect(recipeDetail.recipeId) {
        recipeDetail.recipeId?.let { viewModel.getRecipeDetail(it) }
    }*/

    Box {
        RecipeDetailContent(recipeDetail, scrollState)
        RecipeDetailTopBar(recipeDetail, scrollState, navController)
    }
}

@Composable
fun RecipeDetailTopBar(recipe: RecipeDetail, scrollState: LazyListState, navController: NavController) {
    val imageHeight = 344.dp
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    Box(
        modifier = Modifier
            .height(400.dp)
            .offset { IntOffset(x = 0, y = -offset) }
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(imageHeight)
                    .graphicsLayer { alpha = 1f - offsetProgress }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.strawberry_pie_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.name.toString(),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            IconButton(
                onClick = {
                    navController.navigate("recipe")
                },
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(48.dp)
                    .background(
                        Color("#F9D8D8".toColorInt()),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "RecipeTopBar Page",
                    tint = Color("#E23E3E".toColorInt())
                )
            }
        }
    }
}

@Composable
fun RecipeDetailContent(recipe: RecipeDetail, scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = 400.dp), state = scrollState) {
        item {
            BasicInfo(recipe)
            IngredientsHeader()
            //    IngredientsList(recipe)
            Steps(recipe)
        }
    }
}


@Composable
fun BasicInfo(recipe: RecipeDetail) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(
            iconResource = R.drawable.ic_clock,
            text = recipe.totalTime.toString())
        InfoColumn(
            iconResource = R.drawable.ic_star,
            text = recipe.makingAmount.toString())
    }
}

@Composable
fun IngredientsHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = 125.dp, vertical = 16.dp)
            .clip(medium)
            .background(LightGray)
            .width(180.dp)
            .height(44.dp)
    ) {
        Text(
            text = "Ingredients",
            color = Color("#E23E3E".toColorInt()),
            fontSize = 25.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun Steps(recipe: RecipeDetail) {

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Construction",
            color = Color("#E23E3E".toColorInt()),
            fontSize = 25.sp,
            textAlign = TextAlign.Start,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = LightGray
            )
        ) {
            Text(
                text = recipe.description.toString(),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

    /*
@Composable
fun IngredientsList(recipe: RecipeDetail) {
    EasyGrid(nColumns = 3, items = recipe.ingredients.subList()) {
        IngredientCard(it.toString(), Modifier)
    }
}


@Composable
fun IngredientCard(
    name: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.width(100.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center


        )
    }
}
*/

@Composable
fun <T> EasyGrid(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        for (i in items.indices step nColumns) {
            Row {
                for (j in 0 until nColumns) {
                    if (i + j < items.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResource: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Pink,
            modifier = Modifier.height(50.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    color: Color = Gray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = small,
        colors = ButtonDefaults.buttonColors(
            contentColor = color,
            containerColor = Color.White
        ),
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(painterResource(id = iconResource), contentDescription = null)
    }
}
