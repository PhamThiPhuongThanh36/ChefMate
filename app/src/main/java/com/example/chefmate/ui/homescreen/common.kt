@file:Suppress("UNUSED_EXPRESSION")

package com.example.chefmate.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.model.Recipe

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
                fontSize = 18.sp,
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
fun SearchBar(content: String, onValueChange: (String) -> Unit, placeholder: String, trailingIcons: @Composable (() -> Unit)? = null, onTrailingIconClick: (() -> Unit)? = null) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        TextField(
            value = content,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFADAEBC)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource( id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(10.dp, shape = CircleShape)
        )
    }
}

@Composable
fun Label(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color(0xFF000000),
        fontWeight = FontWeight(600),
        modifier = modifier
    )
}

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
fun BigCard(recipe: Recipe) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.8f)
            .height(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = recipe.image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Text(
                text = recipe.recipeName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp)
            )
            Text(
                text = recipe.userName,
                fontSize = 12.sp,
                color = Color(0xFFF97316),
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp)
                    .background(Color(0xFFFFEDD5))
                    .padding(4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp)
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
            }
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        val recipe = Recipe(
            1,
            1,
            "Sườn xào",
            "https://cdn.tgdd.vn/2021/10/CookDish/cach-lam-nuoc-mam-tac-avt-1200x676.jpg",
            "2",
            3,
            3,
            4,
            "",
            "Thanh",
            "",
            false
        )
        BigCard(recipe)
    }
}
