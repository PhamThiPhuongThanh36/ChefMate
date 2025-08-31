package com.example.chefmate.ui.shopping

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.ui.homescreen.Header
import com.example.chefmate.viewmodel.ShoppingViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun ShoppingScreen(
    shoppingId: Int, // Thêm param này
    shoppingViewModel: ShoppingViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
    ) {
        val context = LocalContext.current
        var listShoppingItems by remember { mutableStateOf<List<ShoppingItemEntity>>(emptyList()) }
        var listShoppingItemsIngredient by remember { mutableStateOf<List<IngredientEntity>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(shoppingId) {
            if (shoppingId != -1) { // Chỉ fetch nếu id valid
                shoppingViewModel.getShoppingItemsById(shoppingId).collect { items ->
                    listShoppingItems = items

                    val ingredients = mutableListOf<IngredientEntity>()
                    items.forEach { item ->
                        item.ingredientId?.let { ingId ->
                            val ingredient = shoppingViewModel.getIngredientById(ingId).first() // Lấy 1 lần
                            ingredients.add(ingredient)
                        }
                    }
                    listShoppingItemsIngredient = ingredients // Set sau khi fetch all
                }
            }
        }

        Header(
            title = "Danh sách mua sắm",
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "back",
                    tint = Color(0xFFFFFFFF)
                )
            },
            onClickLeadingIcon = { navController.popBackStack() },
        )

        if (listShoppingItems.isEmpty()) {
            Text(
                text = "Chưa có nguyên liệu nào",
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp)
            )
        } else {
            LazyColumn {
                items(listShoppingItems.size) { index ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = listShoppingItems[index].status,
                                onCheckedChange = { newStatus ->
                                    coroutineScope.launch {
                                        // Cập nhật DB
                                        val siId = listShoppingItems[index].siId
                                        if (siId != null) {
                                            shoppingViewModel.updateStatusShoppingItem(newStatus, siId)
                                            // Cập nhật danh sách để kích hoạt recompose
                                            listShoppingItems = listShoppingItems.toMutableList().apply {
                                                this[index] = this[index].copy(status = newStatus)
                                            }
                                        }
                                    }
                                }
                            )
                            if (index < listShoppingItemsIngredient.size) { // An toàn
                                Text(text = "${listShoppingItemsIngredient[index].ingredientName} ${listShoppingItemsIngredient[index].weight} ${listShoppingItemsIngredient[index].unit}")
                            }
                        }
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
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA1A1A1),
                    contentColor = Color(0xFFFFFFFF)
                ),
                onClick = {},
                modifier = Modifier
                    .width(120.dp)
            ) {
                Text(
                    text = "Bổ sung"
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF97518),
                    contentColor = Color(0xFFFFFFFF)
                ),
                onClick = {
                    coroutineScope.launch {
                        shoppingViewModel.updateStatusShoppingList(true, shoppingId)
                        DataStoreHelper.finishShopping(context = context)
                        navController.navigate("mainAct")
                    }
                },
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Hoàn thành"
                )
            }
        }
    }
}

@Preview
@Composable
fun ShoppingScreenPreview() {
    ShoppingScreen(shoppingId = 1, shoppingViewModel = hiltViewModel(), rememberNavController())
}