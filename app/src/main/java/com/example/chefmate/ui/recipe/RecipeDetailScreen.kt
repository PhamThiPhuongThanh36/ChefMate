package com.example.chefmate.ui.recipe

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.model.Ingredient
import com.example.chefmate.model.Recipe
import com.example.chefmate.model.Step
import com.example.chefmate.common.*

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun RecipeScreen(recipe: Recipe) {
    val lazyListState1 = rememberLazyListState()
    val lazyListState2 = rememberLazyListState()
    val isShowStep by remember { derivedStateOf { lazyListState1.firstVisibleItemScrollOffset == 0 } }
    val isShowIngredient by remember { derivedStateOf { lazyListState2.firstVisibleItemScrollOffset == 0 } }
    val offsetX by animateFloatAsState(
        targetValue = if (isShowStep) 0f else -150f,
        animationSpec = tween(durationMillis = 500),
        label = "offsetX"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        val ingredients = remember {
            mutableStateListOf(
                Ingredient(1, 1, "Hành lá", 10.0, "lá"),
                Ingredient(2, 1, "Ớt", 5.0, "trái"),
                Ingredient(3, 1, "Nước mắm", 30.0, "ml"),
                Ingredient(4, 1, "Tỏi", 3.0, "tép")
            )
        }

        val step1 = Step(1,1,"Đập trứng cho vào tô cùng với 1 muỗng cà phê nước mắm, 1 muỗng cà phê hạt nêm và đánh trứng cho tan ra hết.")
        val step2 = Step(1,1,"Cà chua mua về rửa sạch, cắt múi cau. Hành tím lột vỏ, rửa sạch rồi cắt lát. Hành lá rửa sạch cắt nhỏ.")
        val step3 = Step(1,1,"Bắc nồi lên bếp, cho vào nồi 1/2 muỗng canh dầu ăn và phi thơm hành tím. Kế đến, cho cà chua vào xào khoảng 2 phút cho cà chua chín.")
        val step4 = Step(1,1,"Canh sôi lại 1 lần nữa thì nêm nếm lại cho vừa ăn, múc ra tô, rắc hành lá và tiêu xay là hoàn thành.")
        val step5 = Step(1,1,"Canh cà chua trứng có màu sắc bắt mắt, hương thơm từ hành lá và tiêu xay rất hấp dẫn.")
        val steps = listOf(step1, step2, step3, step4, step5)
        Header(
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            },
            onClickLeadingIcon = {
                // Handle back button click
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
            visible =  isShowStep,
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
                    painter = rememberAsyncImagePainter(model = recipe.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(350.dp, 260.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        AnimatedVisibility(
            visible = isShowIngredient,
        ) {
            LazyColumn(
                state = lazyListState2
            ) {
                item {
                    Text(
                        text = recipe.recipeName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight(600),
                        modifier = Modifier
                            .padding(start = 30.dp, top = 10.dp)
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 30.dp, top = 10.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = recipe.userImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .border(1.dp, Color(0xFFF97316), shape = CircleShape)
                        )
                        Text(
                            text = recipe.userName,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                    }
                }
                item {
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
                            text = recipe.likeQuantity.toString(),
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
                            text = recipe.viewCount.toString(),
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
                            text = recipe.viewCount.toString(),
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
                            text = recipe.ration.toString(),
                            color = Color(0xFF6B7280),
                            modifier = Modifier
                                .padding(end = 20.dp, start = 5.dp)
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Nguyên liệu",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Cách thực hiện",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                )
                Box(
                    modifier = Modifier
                        .offset(x = offsetX.dp)
                        .height(6.dp)
                        .width(90.dp)
                        .background(Color(0xFFFF7346))
                )
            }
        }
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
                text = "${recipe.ration} người",
                color = Color(0xFF6B7280),
                modifier = Modifier
                    .padding(end = 20.dp, start = 10.dp)
            )
        }
        AnimatedVisibility(
            visible = isShowStep,
        ) {
            LazyColumn(
            ) {
                items(ingredients.size) { index ->
                    IngredientItem(ingredients[index])
                }
            }
        }
        Label("Cách thực hiện", modifier = Modifier.padding(start = 20.dp, top = 10.dp))
        LazyColumn(
            state = lazyListState1
        ) {
            items(steps.size) { index ->
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

@Composable
fun IngredientItem(ingredient: Ingredient) {
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
fun StepItem(step: Step) {
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
    RecipeScreen(recipe)
}