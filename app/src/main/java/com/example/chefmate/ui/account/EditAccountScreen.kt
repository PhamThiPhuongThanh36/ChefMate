package com.example.chefmate.ui.account

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import com.example.chefmate.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.api.ApiClient
import com.example.chefmate.api.ApiConstant
import com.example.chefmate.api.updateProfileRetrofit
import com.example.chefmate.common.DefaultTextField
import com.example.chefmate.common.DefaultTextFieldPassword
import com.example.chefmate.common.LoadingScreen
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.model.UserRequest
import kotlinx.coroutines.launch

@Composable
fun EditAccountScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        fullName = DataStoreHelper.getUserData(context).fullName
        phone = DataStoreHelper.getUserData(context).phone
        email = DataStoreHelper.getUserData(context).email
        imageUri = Uri.parse(DataStoreHelper.getUserData(context).image)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6F6))
            .statusBarsPadding()
    ) {
        var isChangePassword by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                ),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        tint = Color(0xFFBBBBBB),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
            }
            Text(
                text = "Chỉnh sửa thông tin",
                fontSize = 18.sp,
                fontWeight = FontWeight(800),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            shape = CircleShape,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .size(120.dp)
        ) {
            Box{
                Image(
                    painter = if (imageUri == null) painterResource(R.drawable.defaultavatar) else rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = if (imageUri == null) ContentScale.Fit else ContentScale.Crop,
                    modifier = Modifier
                        .size(276.dp, 184.dp)
                        .clickable {
                            if (isChangePassword == false) launcher.launch(arrayOf("image/*"))
                        }
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxSize()

        ) {
            if (isChangePassword) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        tint = Color(0xFF6C6C6C),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .size(18.dp)
                            .clickable {
                                isChangePassword = false
                            }
                    )
                    LabelEdit("Mật khẩu hiện tại")
                    DefaultTextFieldPassword(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        placeholder = "Nhập mật khẩu hiện tại",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    LabelEdit("Mật khẩu mới")
                    DefaultTextFieldPassword(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        placeholder = "Nhập mật khẩu mới",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    LabelEdit("Xác nhận mật khẩu mới")
                    DefaultTextFieldPassword(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Nhập lại mật khẩu mới",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD2D2D2),
                            contentColor = Color(0xFFFFFFFF)
                        ),
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Đổi mật khẩu",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                ) {
                    LabelEdit("Họ và tên")
                    DefaultTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = "Nhập họ và tên",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    LabelEdit("Địa chỉ email")
                    DefaultTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Nhập email",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    LabelEdit("Số điện thoại")
                    DefaultTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        placeholder = "Nhập số điện thoại",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = "Đổi mật khẩu",
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF797979),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .align(Alignment.End)
                            .clickable {
                                isChangePassword = true
                            }
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD2D2D2),
                            contentColor = Color(0xFFFFFFFF)
                        ),
                        onClick = {
                            coroutineScope.launch {
                                isLoading = true
                                val userId = DataStoreHelper.getUserData(context).userId
                                val userData = UserRequest(
                                    userId = userId.toString().toInt(),
                                    fullName = fullName,
                                    email = email,
                                    phone = phone,
                                    image = imageUri.toString()
                                )
                                try {
                                    val response = updateProfileRetrofit(
                                        context = context,
                                        user = userData,
                                        api = ApiClient.createService(ApiConstant.MAIN_URL)
                                    )
                                    response?.let {
                                        if (it.success && it.data != null) {
                                            DataStoreHelper.saveLoginState(
                                                context = context,
                                                isLoggedIn = true,
                                                userId = userId,
                                                username = fullName,
                                                email = email,
                                                phoneNumber = phone,
                                                image = imageUri.toString()
                                            )
                                            isLoading = false
                                            navController.popBackStack()
                                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Lưu thay đổi",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
    if (isLoading) {
        LoadingScreen()
    }
}

@Composable
fun LabelEdit(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        color = Color(0xFF797979),
        modifier = modifier
            .padding(bottom = 10.dp)
    )
}

@Preview
@Composable
fun EditAccountScreenPreview() {
    EditAccountScreen(rememberNavController())
}