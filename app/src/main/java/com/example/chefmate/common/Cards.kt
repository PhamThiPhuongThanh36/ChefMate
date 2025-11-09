package com.example.chefmate.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.api.ApiConstant
import com.example.chefmate.model.Recipe
import java.io.File

@Composable
fun SmallCard(content: String, img: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .height(70.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
        ) {
            Text(
                text = "Công thức\n" + content,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
            Image(
                contentScale = ContentScale.FillHeight,
                painter = painterResource(img),
                contentDescription = null,
                modifier = Modifier
                    .weight(2f)
            )
        }
    }
}


@Composable
fun BigCard(recipe: Recipe, onClick: () -> Unit, modifier: Modifier = Modifier, edit: @Composable (() -> Unit)? = null, delete: @Composable (() -> Unit)? = null ) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        modifier = modifier
            .padding(10.dp)
            .height(280.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            val imageUrl = if (recipe.image.startsWith("/uploads")) {
                ApiConstant.MAIN_URL.trimEnd('/') + recipe.image
            } else {
                recipe.image
            }
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 5.dp, end = 20.dp)
            ) {
                Text(
                    text = recipe.recipeName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (edit != null) {
                    edit()
                }
            }
            Text(
                text = recipe.userName,
                fontSize = 12.sp,
                color = Color(0xFFF97316),
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp)
                    .background(Color(0xFFFFEDD5))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp, end = 20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    tint = Color(0xFFFB923C),
                    contentDescription = null
                )
                Text(
                    text = recipe.likeQuantity.toString(),
                    color = Color(0xFF6B7280),
                    modifier = Modifier
                        .padding(end = 20.dp, start = 5.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    tint = Color(0xFFFB923C),
                    contentDescription = null
                )
                Text(
                    text = recipe.viewCount.toString(),
                    color = Color(0xFF6B7280),
                    modifier = Modifier
                        .padding(end = 20.dp, start = 5.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_comment),
                    tint = Color(0xFFFB923C),
                    contentDescription = null
                )
                Text(
                    text = recipe.viewCount.toString(),
                    color = Color(0xFF6B7280),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                if (delete != null) {
                    delete()
                }
            }
        }
    }
}

@Composable
fun EvaluatedCard(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    var numberStar = remember { mutableStateOf(0) }
    Dialog(
        onDismissRequest = {},
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
            ) {
                val (starRef, contentRef) = createRefs()
                Column(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(200.dp)
                        .constrainAs(contentRef) {
                            start.linkTo(parent.start)
                            top.linkTo(starRef.bottom, margin = -20.dp)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = "Đánh giá",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, bottom = 30.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 60.dp, end = 60.dp, bottom = 30.dp)
                    ) {
                        for (i in 1..5) {
                            Icon(
                                painter = painterResource(R.drawable.ic_full_star),
                                contentDescription = "star",
                                tint = if (i <= numberStar.value) Color(0xFFFFEB3B) else Color(
                                    0xFF9F9F9F
                                ),
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        numberStar.value = i
                                    }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp, end = 50.dp)
                    ) {
                        Text(
                            text = "Đánh giá",
                            color = Color(0xFF424242),
                            fontSize = 15.sp,
                            modifier = Modifier
                                .clickable {
                                    onConfirm()
                                }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Có lẽ để sau",
                            color = Color(0xFF424242),
                            fontSize = 15.sp,
                            modifier = Modifier
                                .clickable {
                                    onDismiss()
                                }
                        )
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFF606060),
                        modifier = Modifier
                            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_full_star),
                    contentDescription = "star image",
                    tint = Color(0xFFFFEB3B),
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(starRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    )
}

@Preview
@Composable
fun CardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        EvaluatedCard({}, {})
    }
}