package com.example.chefmate.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chefmate.R
import com.example.chefmate.common.*

@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmpw by remember { mutableStateOf("") }
        var isHidePassword by remember { mutableStateOf(true) }
        var isHideConfirmPassword by remember { mutableStateOf(true) }

        Title("Đăng ký", "Chào mừng đến với ChefMate")
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            CustomEditText(label = "Họ và tên", content = name, onValueChange = { name = it}, "Vui lòng nhập họ và tên")
            CustomEditText(label = "Số điện thoại", content = phone, onValueChange = { phone = it}, "Vui lòng nhập số điện thoại")
            CustomEditText(label = "Email", content = email, onValueChange = { email = it}, "Vui lòng nhập email")
            CustomEditText(
                label = "Mật khẩu",
                content = password,
                onValueChange = { password = it},
                "Vui lòng nhập mật khẩu",
                trailingIcon = { IconButton(onClick = {isHidePassword = !isHidePassword}, modifier = Modifier.padding(end = 20.dp).size(28.dp))  { Icon( if(isHidePassword) painterResource(R.drawable.ic_eye_close) else painterResource(R.drawable.ic_eye_open), contentDescription = null) }}
            )
            CustomEditText(
                label = "Xác nhận mật khẩu",
                content = confirmpw,
                onValueChange = { confirmpw = it},
                "Vui lòng xác nhận mật khẩu",
                trailingIcon = { IconButton(onClick = {isHideConfirmPassword = !isHideConfirmPassword}, modifier = Modifier.padding(end = 20.dp).size(28.dp)) { Icon( if(isHideConfirmPassword) painterResource(R.drawable.ic_eye_close) else painterResource(R.drawable.ic_eye_open), contentDescription = null) }}
            )
            CustomButton("Đăng ký",
                onClick = {},
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignUpScreen()
}