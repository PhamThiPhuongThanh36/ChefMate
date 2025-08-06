package com.example.chefmate.ui.login

import android.graphics.LinearGradient
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Composable
fun CustomEditText(label: String,content: String, onValueChange: (String) -> Unit, placeholder: String, trailingIcon: @Composable (() -> Unit)? = null) {
    Text(
        text = label,
        fontSize = 18.sp,
        fontWeight = FontWeight(600),
        modifier = Modifier
            .padding(start = 55.dp, end = 40.dp, top = 30.dp)
    )
    TextField(
        value = content,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF737373)
            )
        },
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF000000),
            unfocusedTextColor = Color(0xFF000000),
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
            .shadow(10.dp, RoundedCornerShape(50.dp)),
    )
}

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

@Preview
@Composable
fun TitlePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
//        CustomButton("Sign In", modifier = Modifier) { }
    }
}
