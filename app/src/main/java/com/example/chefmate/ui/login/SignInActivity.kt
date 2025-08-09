package com.example.chefmate.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefmate.R
import com.example.chefmate.common.*

@Composable
fun SignInActivity() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isHidePassword by remember { mutableStateOf(true) }
        Title("Đăng nhập", "Chào mừng trở lại ChefMate")
        CustomEditText("Số điện thoại", phone, { phone = it }, "Vui lòng nhập số điện thoại")
        CustomEditText(
            label = "Mật khẩu",
            content = password,
            onValueChange = { password = it },
            placeholder = "Vui lòng nhập mật khẩu",
            trailingIcon = {
                IconButton(
                    onClick = { isHidePassword = !isHidePassword },
                    modifier = Modifier.padding(end = 20.dp).size(28.dp)
                ) {
                    Icon(
                        if (isHidePassword) painterResource(R.drawable.ic_eye_close) else painterResource(
                            R.drawable.ic_eye_open
                        ), contentDescription = null
                    )
                }
            }
        )
        CustomButton(
            text = "Đăng nhập",
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )
        Text(
            text = "Tạo tài khoản mới",
            color = Color(0xFFA7ACC4),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .clickable {

                }
        )
    }
}

@Preview
@Composable
fun SignInActivityPreview() {
    SignInActivity()
}