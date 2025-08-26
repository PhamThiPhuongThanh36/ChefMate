package com.example.chefmate.ui.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionScene
import com.example.chefmate.common.CustomButton
import com.example.chefmate.common.EditTextWithPlaceholder
import com.example.chefmate.common.EditTextWithouthDescripe
import com.example.chefmate.common.Label

@Composable
fun AddManualIngredientScreen(ingredientName: String, onNameChange: (String) -> Unit, ingredientWeight: String, onWeightChange: (String) -> Unit, ingredientUnit: String, onUnitChange: (String) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Label(
            text = "Nhập tên nguyên liệu",
            modifier = Modifier
                .padding(start = 30.dp, top = 20.dp, bottom = 10.dp)
        )
        EditTextWithPlaceholder(
            value = ingredientName,
            onValueChange = onNameChange,
            placeholder = "Tên nguyên liệu",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally)
        )
        Label(
            text = "Nhập định lượng",
            modifier = Modifier
                .padding(start = 30.dp, top = 20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 10.dp)
        ) {
            EditTextWithPlaceholder(
                value = ingredientWeight,
                onValueChange = onWeightChange,
                placeholder = "Khối lượng",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            EditTextWithPlaceholder(
                value = ingredientUnit,
                onValueChange = onUnitChange,
                placeholder = "Đơn vị",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
        }
        CustomButton(
            text = "Thêm",
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(0.4f)
                .padding(top = 20.dp, end = 30.dp)
                .background(color = Color(0xFFF97518), shape = RoundedCornerShape(10.dp))
        )
    }
}

@Preview
@Composable
fun AddManualIngredientScreenPreview() {
    AddManualIngredientScreen(
        "",{},"",{},"",{}, {}
    )
}