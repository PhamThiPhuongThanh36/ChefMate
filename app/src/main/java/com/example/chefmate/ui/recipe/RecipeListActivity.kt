package com.example.chefmate.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.common.BigCard
import com.example.chefmate.common.Header
import com.example.chefmate.database.entity.toRecipe
import com.example.chefmate.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val listRecipes by recipeViewModel.allRecipes.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Header(
            title = "Công thức của tôi",
            modifier = Modifier
                .height(50.dp)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
                .padding(bottom = 60.dp)
        ) {
            items(listRecipes.size) { index ->
                BigCard(
                    listRecipes[index].toRecipe(),
                    onClick = {
                        navController.navigate("recipeDetail/${listRecipes[index].recipeId}")
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth(0.9f),
                    edit = {
                        Text(
                            text = "Edit",
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .background(Color(0xFF3F51B5), shape = RoundedCornerShape(3.dp))
                                .clickable {
                                    navController.navigate("addRecipe/${listRecipes[index].recipeId}")
                                }
                        )
                    },
                    delete = {
                        Text(
                            text = "Delete",
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight(600),
                            modifier = Modifier
                                .background(Color(0xFFFF0000), shape = RoundedCornerShape(3.dp))
                                .clickable {
                                    coroutineScope.launch {
                                        listRecipes[index].recipeId?.let {
                                            recipeViewModel.deleteRecipeById( it )
                                        }
                                    }
                                }
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun RecipeListScreenPreview() {
    RecipeListScreen(rememberNavController())
}