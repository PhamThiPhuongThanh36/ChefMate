package com.example.chefmate.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.common.BigCard
import com.example.chefmate.common.Header
import com.example.chefmate.database.entity.toRecipe
import com.example.chefmate.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val listRecipes by recipeViewModel.allRecipes.collectAsState(initial = emptyList())
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
        ) {
            items(listRecipes.size) { index ->
                BigCard(listRecipes[index].toRecipe()) {
                    navController.navigate("recipeDetail/${listRecipes[index].recipeId}")
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeListScreenPreview() {
    RecipeListScreen(rememberNavController())
}