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
import com.example.chefmate.R
import com.example.chefmate.model.Recipe
import com.example.chefmate.common.*

@Composable
fun HomeScreen() {
    val lazyListState = rememberLazyListState()
    val isShowPopular by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset == 0 } }
    Log.d("HomeScreen", "isShowPopular: $isShowPopular")

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
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

        val recipe1 = Recipe(
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
            "",
            false
        )

        val recipe2 = Recipe(
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
            "",
            false
        )
        val recipe3 = Recipe(
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
            "",
            false
        )
        val listRecipes = listOf(recipe1, recipe2, recipe3)
        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
        ) {
            items(listRecipes.size) { index ->
                BigCard(
                    listRecipes[index],
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
    HomeScreen()
}