package com.example.chefmate.ui.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chefmate.R
import com.example.chefmate.model.Recipe
import com.example.chefmate.common.*
import com.example.chefmate.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.map

@Composable
fun HomeScreen(recipeViewModel: RecipeViewModel) {
    val lazyListState = rememberLazyListState()
    val isShowPopular by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset == 0 } }
    Log.d("HomeScreen", "isShowPopular: $isShowPopular")

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        val recipes = recipeViewModel.allRecipes.map {
            it.map { recipeEntity ->
                Recipe(
                    recipeId = recipeEntity.recipeId,
                    recipeName = recipeEntity.recipeName,
                    image = recipeEntity.image,
                    cookingTime = recipeEntity.cookingTime,
                    ration = recipeEntity.ration,
                    viewCount = recipeEntity.viewCount,
                    likeQuantity = recipeEntity.likeQuantity,
                    isPublic = false,
                    createdAt = "",
                    userName = "",
                    userImage = ""
                )
            }
        }.collectAsState(initial = emptyList())
        var searchText by remember { mutableStateOf("") }
        val tagsList = listOf("đồ uống", "salad", "nước chấm", "món chính", "món súp", "món chay")
        val imgsList = listOf(R.drawable.lau, R.drawable.lauhaisan, R.drawable.matcha, R.drawable.matchayoguruto, R.drawable.misoshiru, R.drawable.misopoteto)
        Header(
            title = "Nấu ngon",
            onClickSave = {},
            onClickTrailingIcon = {},
            onClickLeadingIcon = {},
            modifier = Modifier
                .height(50.dp)
        )
        SearchBar(
            content = searchText,
            onValueChange = { searchText = it },
            placeholder = "Tìm kiếm công thức",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally)
        )
        Label(
            text = "Phổ biến",
            modifier = Modifier
                .padding(start = 40.dp, top = 20.dp))
        AnimatedVisibility(
            visible = isShowPopular,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 30.dp, end = 30.dp ,top = 10.dp, bottom = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF))
            ) {
                items(tagsList.size) { index ->
                    SmallCard(tagsList[index], imgsList[index])
                }
            }
        }
        Label("Top thịnh hành",
            modifier = Modifier
                .padding(start = 40.dp, top = 20.dp)
        )
        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
        ) {
            items(recipes.value.size) { index ->
                BigCard(
                    recipe = recipes.value[index],
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(hiltViewModel())
}