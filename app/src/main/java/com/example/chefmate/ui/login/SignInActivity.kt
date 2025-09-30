package com.example.chefmate.ui.login

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chefmate.R
import com.example.chefmate.api.ApiClient
import com.example.chefmate.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInActivity(userViewModel: UserViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        var isLoading by remember { mutableStateOf(false) }
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isHidePassword by remember { mutableStateOf(true) }
        val coroutine = rememberCoroutineScope()
        val context = LocalContext.current
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
            onClick = {
                coroutine.launch {
                    isLoading = true
                    val response = ApiClient.login(phone, password)
                    if (response != null) {
                        if(response.data != null) {
                            userViewModel.saveLoginState(
                                context = context,
                                userData = response.data
                            )
                            navController.navigate("mainAct")
                        } else {
                            Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                isLoading = false
            },
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
                    navController.navigate("signUp")
                }
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun SignInActivityPreview() {
    SignInActivity(userViewModel = UserViewModel(), rememberNavController())
}