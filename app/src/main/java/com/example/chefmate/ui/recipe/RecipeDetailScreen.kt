package com.example.chefmate.ui.recipe

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.model.Recipe
import com.example.chefmate.common.*
import com.example.chefmate.model.IngredientInput
import com.example.chefmate.model.StepInput
import com.example.chefmate.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.map
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@SuppressLint("UseOfNonLambdaOffsetOverload", "CoroutineCreationDuringComposition")
@Composable
fun RecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    recipeId: Int,
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState1 = rememberLazyListState()
    var selectedPageManual by remember { mutableIntStateOf(-1) }
    val selectedPage by remember {
        derivedStateOf {
            if (selectedPageManual != -1) {
                selectedPageManual
            } else {
                if (lazyListState1.firstVisibleItemIndex == 0) 0
                else lazyListState1.firstVisibleItemIndex - 1
            }
        }
    }
    val isShowStep by remember { derivedStateOf { lazyListState1.firstVisibleItemIndex == 0 } }

    val animateFooter by animateFloatAsState(
        targetValue = if (selectedPage == 0) 0.25f else 0.75f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Footer Offset"
    )

    val recipe by recipeViewModel.getRecipeById(recipeId)
        .collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        val ingredients by remember(recipeId) {
            recipeViewModel.getIngredientsByRecipeId(recipeId)
                .map { ingredientEntities ->
                    ingredientEntities.map { entity ->
                        IngredientInput(
                            entity.ingredientName,
                            entity.weight.toString(),
                            entity.unit
                        )
                    }
                }
        }.collectAsState(initial = emptyList())

        val steps by remember(recipeId) {
            recipeViewModel.getStepsByRecipeId(recipeId)
                .map { stepEntities ->
                    stepEntities.map { entity ->
                        StepInput(1, entity.description)
                    }
                }
        }.collectAsState(initial = emptyList())

        Header(
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            },
            onClickLeadingIcon = {
                navController.popBackStack()
            },
            saveIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            },
            onClickSave = {
                // Handle save button click
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            },
            onClickTrailingIcon = {
                // Handle share button click
            }

        )
        AnimatedVisibility(
            visible = isShowStep
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = recipe?.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(350.dp, 260.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                Text(
                    text = recipe?.recipeName ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = recipe?.userImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .border(1.dp, Color(0xFFF97316), shape = CircleShape)
                    )
                    Text(
                        text = recipe?.userName ?: "",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart),
                        tint = Color(0xFFFB923C),
                        contentDescription = null
                    )
                    Text(
                        text = recipe?.likeQuantity.toString(),
                        color = Color(0xFF6B7280),
                        modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_eye),
                        tint = Color(0xFFFB923C),
                        contentDescription = null
                    )
                    Text(
                        text = recipe?.viewCount.toString(),
                        color = Color(0xFF6B7280),
                        modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        tint = Color(0xFFFB923C),
                        contentDescription = null
                    )
                    Text(
                        text = recipe?.viewCount.toString(),
                        color = Color(0xFF6B7280),
                        modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_oclock),
                        tint = Color(0xFFFB923C),
                        contentDescription = null,
                    )
                    Text(
                        text = recipe?.ration.toString(),
                        color = Color(0xFF6B7280),
                        modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                val pages = listOf("Nguyên liệu", "Cách thực hiện")
                pages.forEachIndexed { index, page ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF)
                        ),
                        modifier = Modifier
                            .width(150.dp)
                            .clickable {
                                coroutineScope.launch {
                                    if (index == 0) lazyListState1.scrollToItem(index = index)
                                    else lazyListState1.scrollToItem(index = index + 1)
                                }
                            }
                            .weight(1f)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            color = if (selectedPage == index) Color(0xFF1B1B1D) else Color(
                                0xFF5A5A60
                            ),
                            text = page,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val horizontalGuideline = createGuidelineFromStart(animateFooter)

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(8.dp)
                        .background(
                            color = Color(0xFFFB923C),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .constrainAs(createRef()) {
                            start.linkTo(horizontalGuideline)
                            end.linkTo(horizontalGuideline)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }

        LazyColumn(
            state = lazyListState1
        ) {
            item {
                Label("Nguyên liệu", modifier = Modifier.padding(start = 20.dp, top = 10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 40.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_little_person),
                        tint = Color(0xFF9CA3AF),
                        contentDescription = null
                    )
                    Text(
                        text = "${recipe?.ration} người",
                        color = Color(0xFF6B7280),
                        modifier = Modifier
                            .padding(end = 20.dp, start = 10.dp)
                    )
                }
            }
            item {
                ingredients.forEach { ingredientInput ->
                    IngredientItem(ingredientInput)
                }
            }
            item {
                Label("Cách thực hiện", modifier = Modifier.padding(start = 20.dp, top = 10.dp))
            }
            item {
                steps.forEachIndexed { index, stepInput ->
                    Column(
                        modifier = Modifier
                            .padding(start = 40.dp, end = 40.dp)
                    ) {
                        Text(
                            text = "Bước ${index + 1}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 5.dp)
                        )
                        StepItem(steps[index])
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: IngredientInput) {
    Column(
        modifier = Modifier.padding(start = 40.dp, top = 5.dp, end = 40.dp)
    ) {
        Text(
            text = "${ingredient.ingredientName} ${ingredient.weight}${ingredient.unit}",
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        ) {
            val dashWidth = 20f
            val gapWidth = 10f
            var x = 0f
            while (x < size.width) {
                drawLine(
                    color = Color(0xFFD0D0D0),
                    start = Offset(x, 0f),
                    end = Offset(x + dashWidth, 0f),
                    strokeWidth = 2f
                )
                x += dashWidth + gapWidth
            }
        }
    }
}

@Composable
fun StepItem(step: StepInput) {
    Text(
        text = step.description,
        fontSize = 14.sp
    )
}

@Preview
@Composable
fun RecipeScreenPreview() {
    val recipe = Recipe(
        1,
        1,
        "Sườn xào",
        "https://cdn.tgdd.vn/2021/10/CookDish/cach-lam-nuoc-mam-tac-avt-1200x676.jpg",
        "2",
        3,
        3,
        4,
        "",
        "Thanh",
        "https://helios-i.mashable.com/imagery/articles/040MMJLdogUu9t7WB5h2Vbv/hero-image.fill.size_1248x702.v1740075757.jpg",
        false
    )
    RecipeScreen(rememberNavController(), recipeId = recipe.recipeId!!)
}