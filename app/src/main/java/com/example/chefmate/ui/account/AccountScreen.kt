package com.example.chefmate.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefmate.R

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush =  Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF97316),
                        Color(0xFFFDBA74)
                    )
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(18.dp)
        ) {
            Text(
                text = "Trang cá nhân",
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE5905B)
                ),
                modifier = Modifier
                    .border(1.dp, Color(0xFF85D0B6), shape = CircleShape)
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.thanhxinh),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(70.dp)
                        )
                    }
                    Column {
                        Row {
                            Text(
                                text = "Phương Thanh",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                modifier = Modifier
                                    .clickable {  }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_edit),
                                    contentDescription = null,
                                    tint = Color(0xFF2E8D8C),
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                                Text(
                                    text = "Sửa",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E8D8C)
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_phone),
                                contentDescription = null,
                                tint = Color(0xFF5A5A60),
                                modifier = Modifier
                                    .size(16.dp)
                            )
                            Text(
                                text = "0845408835",
                                fontSize = 12.sp,
                                color = Color(0xFF5A5A60)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_email),
                                contentDescription = null,
                                tint = Color(0xFF5A5A60),
                                modifier = Modifier
                                    .size(16.dp)
                            )
                            Text(
                                text = "ptpthanh@gmail.com",
                                fontSize = 12.sp,
                                color = Color(0xFF5A5A60)
                            )
                        }
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE1E1E3)
                ),
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 5.dp)
            ) {
                Row {
                    Card(
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_eye_close),
                            contentDescription = null,
                            modifier = Modifier
                                .size(19.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen()
}