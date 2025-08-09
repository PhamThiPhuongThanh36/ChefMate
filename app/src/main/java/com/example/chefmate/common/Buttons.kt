package com.example.chefmate.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF97316)
        ),
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = text,
            color = Color(0xFFFFFFFF),
            fontSize = 18.sp,
            fontWeight = FontWeight(600)
        )
    }
}