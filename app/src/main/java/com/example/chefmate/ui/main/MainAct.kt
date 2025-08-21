package com.example.chefmate.ui.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.ui.account.AccountScreen
import com.example.chefmate.ui.recipe.RecipeListScreen
import kotlinx.coroutines.launch

@Composable
fun MainAct(navController: NavController) {
    val paperState = rememberPagerState (
        initialPage = 0,
        pageCount = { 3 }
    )
    val coroutineScope = rememberCoroutineScope()
    Box(

    ) {
        Scaffold(
            bottomBar = { BottomNavigationBar(
                selectedIndex = paperState.currentPage,
                navController = navController,
                onTabSelected = {
                    coroutineScope.launch {
                        paperState.animateScrollToPage(it)
                    }
                }
            ) }
        ) { _ ->
            HorizontalPager(
                state = paperState,
                userScrollEnabled = false
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when (page) {
                        0 -> {
                            HomeScreen()
                        }
                        1 -> {
                            RecipeListScreen(navController)
                        }
                        2 -> {
                            AccountScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val items = listOf("Trang chủ", "Kho công thức", "Tài khoản")
    val icons = listOf(R.drawable.ic_home, R.drawable.ic_save, R.drawable.ic_person)
    var isShowOption by remember { mutableStateOf(false) }
    val animeOptionsBackgroud by animateFloatAsState(
        targetValue = if (isShowOption) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Option background"
    )

    ConstraintLayout(

    ) {
        val (bottomBarRef, optionsRef, optionBackgroundRef) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBarRef) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Row(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF))
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onTabSelected(index) }
                    ) {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            tint = if (selectedIndex == index) Color(0xFFFB923C) else Color(
                                0xFF4B5563
                            ),
                            contentDescription = item
                        )
                        Text(
                            text = item,
                            fontSize = 12.sp,
                            color = if (selectedIndex == index) Color(0xFFFB923C) else Color(
                                0xFF4B5563
                            )
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                isShowOption = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF97316)
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            modifier = Modifier
                .size(((1f - animeOptionsBackgroud) * 48).dp)
                .constrainAs(optionsRef) {
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(bottomBarRef.top, margin = 24.dp)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_option),
                contentDescription = "Option",
                tint = Color(0xFFFFFFFF)
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .size(animeOptionsBackgroud.dp * 330, animeOptionsBackgroud.dp * 360)
                .background(color = Color(0xFFFB923C), shape = CircleShape)
                .rotate(45f)
                .constrainAs(optionBackgroundRef) {
                    bottom.linkTo(bottomBarRef.bottom, margin = (-10).dp)
                    end.linkTo(parent.end, margin = (-10).dp)
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size((animeOptionsBackgroud * 320).dp, (animeOptionsBackgroud * 350).dp)
                    .background(color = Color(0xA0000000), shape = CircleShape)
                    .rotate(135f)
                    .constrainAs(createRef()) {
                        bottom.linkTo(parent.bottom, margin = (-6).dp)
                        end.linkTo(parent.end, margin = (-6).dp)
                    }
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .rotate(180f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .clickable {
                                navController.navigate("addRecipe")
                            }
                    ) {
                        Text(
                            text = "Thêm công thức",
                            fontSize = 16.sp,
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .padding(end = 15.dp)
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFDADADA), shape = CircleShape)
                                .padding(5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_recipe),
                                contentDescription = "Add recipe",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(38.dp)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    ) {
                        Text(
                            text = "Lập danh sách mua sắm",
                            fontSize = 16.sp,
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .padding(end = 15.dp)
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFDADADA), shape = CircleShape)
                                .padding(5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_cart),
                                contentDescription = "Add recipe",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(38.dp)
                            )
                        }
                    }
                    Button(
                        onClick = {
                            isShowOption = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFFFF),
                            contentColor = Color(0xFF4B5563)
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        ),
                    ) {
                        Text(
                            text = "Đóng"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainActPreview() {
    MainAct(navController = rememberNavController()
    )
}