package com.example.chefmate.ui.recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.common.AddIndredientEditText
import com.example.chefmate.common.CustomButton
import com.example.chefmate.common.EditTextWithouthLabel
import com.example.chefmate.common.Header
import com.example.chefmate.common.Label
import com.example.chefmate.common.TimeDropdown
import com.example.chefmate.model.IngredientInput
import com.example.chefmate.model.StepInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen() {
    var recipeName by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var ration by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Phút") }
    val ingredients = remember {
        mutableStateListOf(IngredientInput("", "", ""))
    }
    val steps = remember {
        mutableStateListOf<StepInput>(StepInput(1, ""))
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Header(
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            },
            onClickLeadingIcon = {
                // Handle back button click
            },
            title = "Thêm công thức"
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 30.dp)
        ) {
            Image(
                painter = if (imageUri == null) painterResource(R.drawable.ic_picture_placeholder) else rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                contentScale = if (imageUri == null) ContentScale.Fit else ContentScale.Crop,
                modifier = Modifier
                    .size(276.dp, 184.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
            EditTextWithouthLabel(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = "Tên công thức",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            )
            EditTextWithouthLabel(
                value = tags,
                onValueChange = { tags = it },
                label = "Tags",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp),
            ) {
                OutlinedTextField(
                    value = cookingTime,
                    onValueChange = { cookingTime = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color(0xFFA3A3A3),
                        unfocusedIndicatorColor = Color(0xFFA3A3A3),
                    ),
                    label = {
                        Text("Thời gian nấu")
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TimeDropdown(
                    selectedTime = selectedUnit,
                    onTimeSelected = { selectedUnit = it },
                    modifier = Modifier
                        .weight(1f)
                )
            }
            EditTextWithouthLabel(
                value = ration,
                onValueChange = { ration = it },
                label = "Khẩu phần ăn",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
                    .padding(top = 15.dp)
            ) {
                Label("Trạng thái")
            }

            val options = listOf("Riêng tư", "Công khai")
            var (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            ) {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFF97518),
                                unselectedColor = Color(0xFFF97518)
                            ),
                            onClick = { onOptionSelected(option) }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
            ) {
                Label("Nguyên liệu")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 10.dp)
            ) {
                ingredients.forEachIndexed{ index, item ->
                    AddIndredientEditText(
                        name = item.ingredientName,
                        onNameChange = { ingredients[index] = item.copy(ingredientName = it) },
                        quantity = item.weight,
                        onQuantityChange = { ingredients[index] = item.copy(ingredientName = it) },
                        unit = item.unit,
                        onUnitChange = { ingredients[index] = item.copy(ingredientName = it) }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .clickable {
                            ingredients.add(IngredientInput("", "", ""))
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_plus_only),
                        contentDescription = null
                    )
                    Text(
                        text = "Thêm nguyên liệu",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
            ) {
                Label("Các bước nấu")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            ) {
                steps.forEachIndexed { index, item ->
                    EditTextWithouthLabel(
                        value = item.description,
                        onValueChange = { steps[index] = item.copy(description = it) },
                        label = "Bước ${index + 1}",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .clickable {
                            steps.add(StepInput(steps.size + 1, ""))
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_plus_only),
                        contentDescription = null
                    )
                    Text(
                        text = "Thêm bước nấu",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
            CustomButton(
                text = "Đăng công thức",
                onClick = {

                },
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun AddRecipeScreenPreview() {
    AddRecipeScreen()
}