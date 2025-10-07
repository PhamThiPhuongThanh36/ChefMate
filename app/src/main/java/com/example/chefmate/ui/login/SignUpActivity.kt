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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.api.ApiClient
import com.example.chefmate.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(userViewModel: UserViewModel, navController: NavController) {
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
        val coroutine = rememberCoroutineScope()
        val context = LocalContext.current
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
                visualTransformation = if (isHidePassword) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = { IconButton(onClick = {isHidePassword = !isHidePassword}, modifier = Modifier.padding(end = 20.dp).size(28.dp))  { Icon( if(isHidePassword) painterResource(R.drawable.ic_eye_close) else painterResource(R.drawable.ic_eye_open), contentDescription = null) }}
            )
            CustomEditText(
                label = "Xác nhận mật khẩu",
                content = confirmpw,
                onValueChange = { confirmpw = it},
                "Vui lòng xác nhận mật khẩu",
                visualTransformation = if (isHideConfirmPassword) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = { IconButton(onClick = {isHideConfirmPassword = !isHideConfirmPassword}, modifier = Modifier.padding(end = 20.dp).size(28.dp)) { Icon( if(isHideConfirmPassword) painterResource(R.drawable.ic_eye_close) else painterResource(R.drawable.ic_eye_open), contentDescription = null) }}
            )
            CustomButton("Đăng ký",
                onClick = {
                    coroutine.launch {
                        val response = userViewModel.requester(name, phone, email, password)
                        if (response != null) {
                            if (response.data != null) {
                                userViewModel.saveLoginState(
                                    context,
                                    userData = response.data
                                )
                                navController.navigate("mainAct")
                            }
                        }
                    }
                },
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
    SignUpScreen(userViewModel = hiltViewModel(), rememberNavController() )
}