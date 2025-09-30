package com.example.chefmate.ui.account

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.common.EvaluatedCard
import com.example.chefmate.common.ItemSetting
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.viewmodel.RecipeViewModel
import com.example.chefmate.viewmodel.UserViewModel

@Composable
fun AccountScreen(userViewModel: UserViewModel, recipeViewModel: RecipeViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(
                brush =  Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF97316),
                        Color(0xFFFDBA74)
                    )
                )
            )
    ) {
        val context = LocalContext.current
        var isShowRating by remember { mutableStateOf(false) }
        val recipeSize = recipeViewModel.allRecipes.collectAsState(emptyList()).value.size

        LaunchedEffect(Unit) {
            userViewModel.isLoggedIn(context)
            userViewModel.getUserData(context)
        }
        val isLoggedIn = userViewModel.isLoggedIn.collectAsState().value
        val user = userViewModel.user.collectAsState().value
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(18.dp)
        ) {
            Text(
                text = "Trang cá nhân",
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE5905B)
                ),
                modifier = Modifier
                    .border(1.dp, Color(0xFF85D0B6), shape = CircleShape)
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            userViewModel.logout(context)
                        }
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
        ) {
            if (isLoggedIn) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 5.dp)
                    ) {
                        Card(
                            shape = CircleShape,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.thanhxinh),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                            )
                        }
                        Column {
                            Row {
                                Text(
                                    text = user?.fullName ?: "No data",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Row(
                                    modifier = Modifier
                                        .clickable {  }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_edit),
                                        contentDescription = null,
                                        tint = Color(0xFF2E8D8C),
                                        modifier = Modifier
                                            .size(16.dp)
                                    )
                                    Text(
                                        text = "Sửa",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E8D8C)
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_phone),
                                    contentDescription = null,
                                    tint = Color(0xFF5A5A60),
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                                Text(
                                    text = user?.phone ?: "No data",
                                    fontSize = 12.sp,
                                    color = Color(0xFF5A5A60),
                                    modifier = Modifier
                                        .padding(start = 3.dp)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_email),
                                    contentDescription = null,
                                    tint = Color(0xFF5A5A60),
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                                Text(
                                    text = user?.email ?: "No data",
                                    fontSize = 12.sp,
                                    color = Color(0xFF5A5A60),
                                    modifier = Modifier
                                        .padding(start = 3.dp)
                                )
                            }
                        }
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE1E1E3)
                    ),
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(40.dp)
                                .background(Color(0xFFD7EDED), shape = CircleShape)
                                .border(1.dp, shape = CircleShape, color = Color(0xFFFFFFFF))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_recipe_book),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("Bạn đã đăng ")
                                    }
                                    withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                                        append("$recipeSize công thức")
                                    }
                                }
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Xem công thức đã đăng",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E8D8C),
                                    fontSize = 12.sp
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_next),
                                    tint = Color(0xFF2E8D8C),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Mời đăng nhập",
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            navController.navigate("signIn")
                        }
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F8FC)
            ),
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
                .border(1.dp, Color(0xFF85D0B6), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                com.example.chefmate.common.Label("Cài đặt", modifier = Modifier.padding(start = 20.dp, bottom = 10.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .border(1.dp, Color(0xFFE1E1E3), shape = RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column {
                        ItemSetting(
                            icon = R.drawable.ic_cart_setting,
                            content = "Lịch sử mua sắm",
                        )
                        ItemSetting(
                            icon = R.drawable.ic_security,
                            content = "Chính sách bảo mật",

                        )
                        ItemSetting(
                            icon = R.drawable.ic_clause,
                            content = "Điều khoản sử dụng"
                        )
                        ItemSetting(
                            icon = R.drawable.ic_question,
                            content = "Hỗ trợ và báo cáo"
                        )
                        ItemSetting(
                            icon = R.drawable.ic_star,
                            content = "Đánh giá",
                            modifier = Modifier
                                .clickable {
                                    isShowRating = true
                                }
                        )
                    }
                }
            }
        }
        if (isShowRating) {
            EvaluatedCard(
                onDismiss = { isShowRating = false },
                onConfirm = { isShowRating = false }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(userViewModel = UserViewModel(), hiltViewModel(), rememberNavController())
}