package com.example.chefmate.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chefmate.common.BigCard
import com.example.chefmate.common.Header
import com.example.chefmate.database.AppDatabase
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.toRecipe
import com.example.chefmate.model.Recipe
import com.example.chefmate.repository.RecipeRepository
import com.example.chefmate.viewmodel.RecipeViewModel
import com.example.chefmate.viewmodel.RecipeViewModelFactory

@Composable
fun RecipeListScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val recipeDao = remember { db.recipeDao() }
    val ingredientDao = remember { db.ingredientDao() }
    val stepDao = remember { db.stepDao() }
    // Khởi tạo Repository
    val recipeRepository = remember { RecipeRepository(recipeDao, ingredientDao, stepDao) }
    // Khởi tạo ViewModel với Repository
    val recipeViewModel: RecipeViewModel = viewModel(
        factory = RecipeViewModelFactory(recipeRepository)
    )
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
//        val recipe1 = Recipe(
//            1,
//            1,
//            "Sườn xào",
//            "https://cdn.tgdd.vn/2021/10/CookDish/cach-lam-nuoc-mam-tac-avt-1200x676.jpg",
//            "2",
//            3,
//            3,
//            4,
//            "",
//            "Thanh",
//            "",
//            false
//        )
//
//        val recipe2 = Recipe(
//            1,
//            1,
//            "Sườn xào",
//            "https://cdn.tgdd.vn/2021/10/CookDish/cach-lam-nuoc-mam-tac-avt-1200x676.jpg",
//            "2",
//            3,
//            3,
//            4,
//            "",
//            "Thanh",
//            "",
//            false
//        )
//        val recipe3 = Recipe(
//            1,
//            1,
//            "Sườn xào",
//            "https://cdn.tgdd.vn/2021/10/CookDish/cach-lam-nuoc-mam-tac-avt-1200x676.jpg",
//            "2",
//            3,
//            3,
//            4,
//            "",
//            "Thanh",
//            "",
//            false
//        )
//        val listRecipes = listOf(recipe1, recipe2, recipe3)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
        ) {
            items(listRecipes.size) { index ->
                BigCard(listRecipes[index].toRecipe())
            }
        }
    }
}

@Preview
@Composable
fun RecipeListScreenPreview() {
    RecipeListScreen()
}