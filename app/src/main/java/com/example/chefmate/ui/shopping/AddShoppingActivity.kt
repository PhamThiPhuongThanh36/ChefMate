package com.example.chefmate.ui.shopping

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.common.CustomButton
import com.example.chefmate.common.Header
import com.example.chefmate.common.Label
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShoppingScreen(navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
    ) {
        var searchInput by remember { mutableStateOf("") }
        val recipes = recipeViewModel.allRecipes.collectAsState(initial = emptyList())
        val selectedRecipes by remember { mutableStateOf<List<RecipeEntity>>(emptyList()) }
        var ingredientName by remember { mutableStateOf("") }
        var ingredientWeight by remember { mutableStateOf("") }
        var ingredientUnit by remember { mutableStateOf("") }
        var isShowAddManualIngredient by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        Header(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color(0xFFFFFFFF)
                )
            },
            onClickLeadingIcon = { navController.popBackStack() },
            title = "Lập danh sách mua sắm",
        )
        com.example.chefmate.common.SearchBar(
            content = searchInput,
            onValueChange = { searchInput = it },
            placeholder = "Tìm công thức đã lưu",
            trailingIcons = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search recipe"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Label(
            text = "Thêm nguyên liệu qua công thức",
            modifier = Modifier
                .padding(start = 18.dp, top = 20.dp, bottom = 10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(recipes.value.size) { index ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    modifier = Modifier
                        .size(180.dp, 105.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    var isChecked by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(7f)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    isChecked = !isChecked
                                }
                            )
                            if (isChecked) {
                                selectedRecipes + recipes.value[index]
                            } else {
                                selectedRecipes.filter { it.recipeId != recipes.value[index].recipeId }
                            }
                            Log.d("selectedRecipes", selectedRecipes.toString())
                            Text(
                                text = recipes.value[index].recipeName,
                                color = Color(0xFF4B5563),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Text(
                                text = "@Ptpt",
                                color = Color(0xFFF97316),
                                fontSize = 10.sp
                            )
                        }
                        Image(
                            rememberAsyncImagePainter(model = recipes.value[index].image),
                            contentDescription = "Recipe image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(11f)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            border = BorderStroke(1.dp, Color(0xFFD6D6D6)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(160.dp)
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                modifier = Modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Label(
                        text = "Thêm nguyên liệu thủ công",
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            isShowAddManualIngredient = true
                        },
                        modifier = Modifier
                            .padding(end = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_ingredient),
                            contentDescription = "add manual ingredient"
                        )
                    }
                }
            }
        }
        if (isShowAddManualIngredient) {
            ModalBottomSheet(
                onDismissRequest = {
                    isShowAddManualIngredient = false
                },
                sheetState = sheetState
            ) {
                AddManualIngredientScreen(
                    ingredientName = ingredientName,
                    onNameChange = { ingredientName = it },
                    ingredientWeight = ingredientWeight,
                    onWeightChange = { ingredientWeight = it },
                    ingredientUnit = ingredientUnit,
                    onUnitChange = { ingredientUnit = it },
                    onClick = {
                        isShowAddManualIngredient = false
                    }
                )
            }
        }
        CustomButton(
            text = "Hoàn thành",
            onClick = {},
            modifier = Modifier
                .padding(top = 20.dp, end = 20.dp)
                .align(Alignment.End)
        )
    }
}


@Preview
@Composable
fun AddShoppingScreenPreview() {
    AddShoppingScreen(navController = rememberNavController(), recipeViewModel = hiltViewModel())
}