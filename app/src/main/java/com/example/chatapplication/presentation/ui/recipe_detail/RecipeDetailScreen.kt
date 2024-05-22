package com.example.chatapplication.presentation.ui.recipe_detail

import androidx.compose.runtime.Composable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chatapplication.R
import com.example.chatapplication.base.ViewState
import com.example.chatapplication.data.remote.model.RecipeDetail
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import com.example.chatapplication.ui.theme.MainColor
import com.example.chatapplication.ui.theme.SecondaryColor
import com.example.chatapplication.ui.theme.White
import com.example.chatapplication.ui.theme.mediumFont
import com.example.chatapplication.ui.theme.regular
import com.example.chatapplication.ui.theme.semibold
import kotlin.math.max
import kotlin.math.min

val medium: CornerBasedShape = RoundedCornerShape(4.dp)

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int?,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    LaunchedEffect(recipeId) {
        recipeId?.let { viewModel.getRecipeDetail(it) }
    }

    Box {
        if (detailState is ViewState.Success) {
            val recipeDetail: RecipeDetail =
                (detailState as ViewState.Success<RecipeDetail>).data


            RecipeDetailContent(recipeDetail, scrollState)
            RecipeDetailTopBar(recipeDetail, scrollState, navController)
        }
    }
}

@Composable
fun RecipeDetailTopBar(recipe: RecipeDetail, scrollState: LazyListState, navController: NavController) {
    val imageHeight = 294.dp
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    Box(
        modifier = Modifier
            .height(350.dp)
            .offset { IntOffset(x = 0, y = -offset) }
            .background(White)

    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(imageHeight)
                    .graphicsLayer { alpha = 1f - offsetProgress }
            ) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.name.toString().lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    fontSize = 20.sp,
                    fontFamily = semibold,
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
                    .padding(top = 10.dp, end = 10.dp)
                    .size(48.dp)
                    .background(
                        MainColor,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "RecipeTopBar Page",
                    tint = SecondaryColor
                )
            }
        }
    }
}

@Composable
fun RecipeDetailContent(recipe: RecipeDetail, scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = 350.dp), state = scrollState) {
        item {
            BasicInfo(recipe)
            IngredientsHeader()
            IngredientsList(recipe)
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
            .background(White)
    ) {
        InfoColumn(
            iconResource = R.drawable.ic_clock,
            text =  if (recipe.totalTime.isNullOrBlank()) "-" else recipe.makingAmount.toString())
        InfoColumn(
            iconResource = R.drawable.ic_star,
            text =  if (recipe.makingAmount.isNullOrBlank()) "-" else recipe.makingAmount.toString())
    }
}

@Composable
fun IngredientsHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(medium)
            .background(White)
    ) {
        Text(
            text = "Ingredients",
            color = SecondaryColor,
            fontSize = 20.sp,
            fontFamily = mediumFont,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun Steps(recipe: RecipeDetail) {

    Column(
        modifier = Modifier.fillMaxHeight()
            .padding(horizontal = 16.dp)
            .background(White),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Construction",
            color = SecondaryColor,
            fontSize = 20.sp,
            fontFamily = mediumFont,
            textAlign = TextAlign.Start,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top =16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Text(
                text = recipe.directions.toString(),
                textAlign = TextAlign.Start,
                fontFamily = regular
            )
        }
    }
}

@Composable
fun IngredientsList(recipe: RecipeDetail) {
    EasyGrid(nColumns = 3, items = recipe.ingredients ?: emptyList()) {
        IngredientCard(it, Modifier)
    }
}

@Composable
fun IngredientCard(
    name: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(MainColor),
        modifier = modifier
            .padding(bottom = 16.dp, start = 4.dp, end = 4.dp)
            .aspectRatio(1f)
            .background(White)
            .clickable { expanded = !expanded } // Tıklama ile genişlet/daralt
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = name,
                fontSize = 13.sp,
                fontFamily = mediumFont,
                textAlign = TextAlign.Center,
                maxLines = if (expanded) Int.MAX_VALUE else 3, // Genişletilmişse satır sınırı yok
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

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
            tint = SecondaryColor,
            modifier = Modifier.height(50.dp)
        )
        Text(text = text, fontFamily = mediumFont)
    }
}
