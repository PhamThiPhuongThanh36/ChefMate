package com.example.chefmate.ui.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chefmate.R
import com.example.chefmate.common.Header

@Composable
fun AddShoppingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Header(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color(0xFFFFFFFF)
                )
            },
            onClickLeadingIcon = { /*TODO*/ },
            title = "Thêm vào danh sách",
        )
    }
}

@Preview
@Composable
fun AddShoppingScreenPreview() {
    AddShoppingScreen()
}