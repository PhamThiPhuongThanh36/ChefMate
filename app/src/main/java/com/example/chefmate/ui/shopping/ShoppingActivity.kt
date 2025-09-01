package com.example.chefmate.ui.shopping

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.common.CustomAlertDialog
import com.example.chefmate.common.CustomDiaLogEditIngredient
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.ui.homescreen.Header
import com.example.chefmate.viewmodel.ShoppingViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun ShoppingScreen(
    shoppingId: Int,
    shoppingViewModel: ShoppingViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
    ) {
        var newIngredientName by remember { mutableStateOf("") }
        var newIngredientWeight by remember { mutableStateOf("") }
        var newIngredientUnit by remember { mutableStateOf("") }
        var isShowAddIngredient by remember { mutableStateOf(false) }
        var isShowEditIngredient by remember { mutableStateOf(false) }
        var isShowDeleteIngredient by remember { mutableStateOf(false) }
        val context = LocalContext.current
        var ingredientEdit by remember { mutableStateOf<IngredientEntity?>(null) }
        var ingredientEditId by remember { mutableStateOf<Int?>(null) }
        var shoppingDeleteId by remember { mutableStateOf<Int?>(null) }
        var ingredientDelete by remember { mutableStateOf<IngredientEntity?>(null) }
        var listShoppingItems by remember { mutableStateOf<List<ShoppingItemEntity>>(emptyList()) }
        var listShoppingItemsIngredient by remember { mutableStateOf<List<IngredientEntity>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()
        var refreshKey by remember { mutableStateOf(0) }

        fun refreshLists() {
            coroutineScope.launch {
                if (shoppingId != -1) {
                    shoppingViewModel.getShoppingItemsById(shoppingId).collect { items ->
                        val sortedItems = items.sortedBy { it.status }
                        listShoppingItems = sortedItems
                        val ingredients = sortedItems.mapNotNull { item ->
                            item.ingredientId?.let { ingId ->
                                shoppingViewModel.getIngredientById(ingId).first()
                            }
                        }
                        listShoppingItemsIngredient = ingredients
                    }
                }
            }
        }

        LaunchedEffect(shoppingId, refreshKey) {
            if (shoppingId != -1) {
                refreshLists()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
        ) {
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
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
                                                refreshKey++
                                            }
                                        }
                                    }
                                )
                                if (index < listShoppingItemsIngredient.size) {
                                    Text(
                                        text = "${listShoppingItemsIngredient[index].ingredientName} ${formatWeight(listShoppingItemsIngredient[index].weight)} ${listShoppingItemsIngredient[index].unit}",
                                        textDecoration = if(listShoppingItems[index].status) TextDecoration.LineThrough else TextDecoration.None
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(R.drawable.ic_edit),
                                    contentDescription = "edit ingredient",
                                    tint = Color(0xFF4C4C4C),
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .clickable {
                                            ingredientEditId = listShoppingItems[index].ingredientId
                                            ingredientEdit = listShoppingItemsIngredient[index].copy()
                                            isShowEditIngredient = true
                                        }
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_delete),
                                    contentDescription = "delete ingredient",
                                    tint = Color(0xFF4C4C4C),
                                    modifier = Modifier
                                        .clickable {
                                            shoppingDeleteId = listShoppingItems[index].siId
                                            ingredientDelete = listShoppingItemsIngredient[index].copy()
                                            isShowDeleteIngredient = true
                                        }
                                )
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
                if(isShowEditIngredient) {
                    CustomDiaLogEditIngredient(
                        title = "Chỉnh sửa nguyên liệu",
                        onDismiss = {
                            isShowEditIngredient = false
                        },
                        ingredientName = ingredientEdit?.ingredientName ?: "",
                        onNameChange = {
                            ingredientEdit = ingredientEdit?.copy(ingredientName = it)
                        },
                        ingredientWeight = ingredientEdit?.weight.toString(),
                        onWeightChange = {
                            ingredientEdit = ingredientEdit?.copy(weight = it.toDouble())
                        },
                        ingredientUnit = ingredientEdit?.unit ?: "",
                        onUnitChange = {
                            ingredientEdit = ingredientEdit?.copy(unit = it)
                        },
                        confirmText = "Lưu",
                        onConfirm = {
                            coroutineScope.launch {
                                shoppingViewModel.updateIngredient(
                                    IngredientEntity(
                                        ingredientId = ingredientEdit?.ingredientId,
                                        ingredientName = ingredientEdit?.ingredientName ?: "",
                                        weight = ingredientEdit?.weight ?: 0.0,
                                        unit = ingredientEdit?.unit ?: ""
                                    )
                                )
                                refreshKey++
                            }
                            isShowEditIngredient = false
                        }
                    )
                }
                if (isShowDeleteIngredient) {
                    CustomAlertDialog(
                        title = "Xóa nguyên liệu",
                        content = "Bạn có chắc chắn xóa nguyên liệu ${ingredientDelete?.ingredientName} ${ingredientDelete?.weight} ${ingredientDelete?.unit}?",
                        onDismiss = {
                            isShowDeleteIngredient = false
                        },
                        confirmText = "Xóa",
                        onConfirm = {
                            coroutineScope.launch {
                                shoppingViewModel.deleteShoppingItemById(shoppingDeleteId ?: 0)
                            }
                            refreshKey++
                            isShowDeleteIngredient = false
                        }
                    )
                }
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA1A1A1),
                contentColor = Color(0xFFFFFFFF)
            ),
            onClick = {
                isShowAddIngredient = true
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(start = 40.dp, bottom = 50.dp)
                .align(Alignment.BottomStart)
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
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(end = 40.dp, bottom = 50.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Hoàn thành"
            )
        }
        if (isShowAddIngredient) {
            CustomDiaLogEditIngredient(
                title = "Thêm nguyên liệu",
                onDismiss = {
                    isShowAddIngredient = false
                },
                ingredientName = newIngredientName,
                onNameChange = { newIngredientName = it },
                ingredientWeight = newIngredientWeight,
                onWeightChange = { newIngredientWeight = it },
                ingredientUnit = newIngredientUnit,
                onUnitChange = { newIngredientUnit = it },
                confirmText = "Thêm",
                onConfirm = {
                    val name = newIngredientName.trim()
                    val weight = newIngredientWeight.trim().toDoubleOrNull()
                    val unit = newIngredientUnit.trim()

                    if (name.isBlank() || weight == null || unit.isBlank()) {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            val newIngredientId = shoppingViewModel.insertIngredient(
                                IngredientEntity(
                                    ingredientName = name,
                                    weight = weight,
                                    unit = unit
                                )
                            ).toInt()
                            shoppingViewModel.insertShoppingItem(
                                ShoppingItemEntity(
                                    shoppingId = shoppingId,
                                    ingredientId = newIngredientId,
                                    status = false
                                )
                            )
                            refreshKey++
                        }
                        newIngredientName = ""
                        newIngredientWeight = ""
                        newIngredientUnit = ""
                        isShowAddIngredient = false
                    }
                }
            )
        }
    }
}

fun formatWeight(weight: Double): String {
    return if (weight == weight.toInt().toDouble()) {
        weight.toInt().toString()
    } else {
        weight.toString()
    }
}

@Preview
@Composable
fun ShoppingScreenPreview() {
    ShoppingScreen(shoppingId = 1, shoppingViewModel = hiltViewModel(), rememberNavController())
}