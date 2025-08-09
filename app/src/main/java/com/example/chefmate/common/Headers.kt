package com.example.chefmate.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    leadingIcon: @Composable (() -> Unit)? = null, onClickLeadingIcon: (() -> Unit)? = null,
    title: String? = null,
    saveIcon: @Composable (() -> Unit)? = null, onClickSave: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null, onClickTrailingIcon: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF97316),
                        Color(0xFFFA872F),
                        Color(0xFFFDBA74)
                    )
                )
            )
            .padding(10.dp)
    ) {
        if (leadingIcon != null) {
            IconButton(
                onClick = onClickLeadingIcon!!,
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                leadingIcon()
            }
        }
        if (title != null) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (saveIcon != null) {
            IconButton(
                onClick = onClickSave!!,
                modifier = Modifier
                    .padding(end = 12.dp)
            ) {
                saveIcon()
            }
        }
        if (trailingIcon != null) {
            IconButton(
                onClick = onClickTrailingIcon!!,
                modifier = Modifier
                    .padding(end = 20.dp)
            ) {
                trailingIcon()
            }
        }
    }
}

@Composable
fun Title(h1: String, h2: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color( 0xFFF97316),
                        Color(0xFFFA872F),
                        Color(0xFFFDBA74)
                    )
                ),
                shape = RoundedCornerShape(bottomEnd = 100.dp)
            )
    ) {
        Text(
            text = h1,
            color = Color(0xFFFFFFFF),
            fontSize = 32.sp,
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(top = 90.dp, start = 40.dp)
        )
        Text(
            text = h2,
            color = Color(0xFFFFFFFF),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 20.dp, start = 40.dp, bottom = 30.dp)
        )
    }
}