package com.example.chefmate.ui.recipe

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.common.AddIngredientEditText
import com.example.chefmate.common.CustomButton
import com.example.chefmate.common.EditTextWithouthDescripe
import com.example.chefmate.common.Header
import com.example.chefmate.common.Label
import com.example.chefmate.common.TimeDropdown
import com.example.chefmate.common.saveImageToInternalStorage
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.model.IngredientInput
import com.example.chefmate.model.StepInput
import com.example.chefmate.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AddEditRecipeScreen(recipeId: Int, navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var recipeName by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var ration by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Phút") }
    val ingredients = remember { mutableStateListOf<IngredientEntity>() }
    val steps = remember {
        mutableStateListOf(StepInput( ""))
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        val recipe = recipeViewModel.getRecipeById(recipeId).first()
        recipe?.let {
            recipeName = recipe.recipeName
            imageUri = recipe.image.toUri()
            cookingTime = recipe.cookingTime
            ration = recipe.ration.toString()
            selectedUnit = "Phút"
        }
        val ingredientEntities = recipeViewModel.getIngredientsByRecipeId(recipeId).first()
        ingredients.clear()
        ingredients.addAll(ingredientEntities)

        val stepEntities = recipeViewModel.getStepsByRecipeId(recipeId).first()
        steps.clear()
        steps.addAll(stepEntities.map { StepInput(it.description) })
    }
    Log.d("Ingredients: ", ingredients.toString())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
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
                navController.popBackStack()
            },
            title = if (recipeId == -1) "Thêm công thức" else "Cập nhật công thức"
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
            EditTextWithouthDescripe(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = "Tên công thức",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            )
            EditTextWithouthDescripe(
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
                EditTextWithouthDescripe(
                    value = cookingTime,
                    onValueChange = { cookingTime = it },
                    label = "Thời gian nấu",
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
            EditTextWithouthDescripe(
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
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
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
                    AddIngredientEditText(
                        name = item.ingredientName,
                        onNameChange = { ingredients[index] = item.copy(ingredientName = it) },
                        quantity = item.weight,
                        onQuantityChange = { ingredients[index] = item.copy(weight = it) },
                        unit = item.unit,
                        deleteIngredient = {
                            if (ingredients.size > 1) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_minus),
                                    contentDescription = "minus ingredient",
                                    tint = Color(0xFFBF6A02),
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                        .size(17.dp)
                                        .clickable {
                                            ingredients.removeAt(index)
                                        }
                                )
                            } else { }
                        },
                        onUnitChange = { ingredients[index] = item.copy(unit = it) },
                        modifier = Modifier
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .clickable {
                            ingredients.add(IngredientEntity(-1, -1, "", "", ""))
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
                    EditTextWithouthDescripe(
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
                            steps.add(StepInput(""))
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
                text = if (recipeId == -1) "Đăng công thức" else "Cập nhật công thức",
                onClick = {
                    val currentDateTime = java.time.LocalDateTime.now().toString()
                    val savedImagePath = imageUri?.let { uri ->
                        saveImageToInternalStorage(context, uri)
                    }
                    coroutineScope.launch(Dispatchers.IO) {
                        if (recipeId == -1) {
                            val newRecipe = RecipeEntity(
                                userId = 1,
                                recipeName = recipeName,
                                image = savedImagePath.toString(),
                                cookingTime = cookingTime,
                                ration = ration.toInt(),
                                viewCount = 0,
                                likeQuantity = 0,
                                createdAt = currentDateTime
                            )
                            val newRecipeId = recipeViewModel.insertRecipe(newRecipe).toInt()
                            val ingredientEntities = ingredients.map { ingredientInput ->
                                IngredientEntity(
                                    recipeId = newRecipeId,
                                    ingredientName = ingredientInput.ingredientName,
                                    weight = ingredientInput.weight,
                                    unit = ingredientInput.unit
                                )
                            }.filter { it.ingredientName.isNotBlank() }
                            recipeViewModel.insertIngredients(ingredientEntities)

                            val stepEntities = steps.map { stepInput ->
                                com.example.chefmate.database.entity.StepEntity(
                                    recipeId = newRecipeId,
                                    description = stepInput.description
                                )
                            }.filter { it.description.isNotBlank() }
                            recipeViewModel.insertSteps(stepEntities)

                            withContext(Dispatchers.Main) {
                                navController.popBackStack()
                            }
                        } else {
                            val newRecipe = RecipeEntity(
                                recipeId = recipeId,
                                userId = 1,
                                recipeName = recipeName,
                                image = savedImagePath.toString(),
                                cookingTime = cookingTime,
                                ration = ration.toInt(),
                                viewCount = 0,
                                likeQuantity = 0,
                                createdAt = currentDateTime
                            )
                            recipeViewModel.updateRecipe(newRecipe)
                            val ingredientEntities = ingredients.map { ingredientInput ->
                                IngredientEntity(
                                    recipeId = recipeId,
                                    ingredientName = ingredientInput.ingredientName,
                                    weight = ingredientInput.weight,
                                    unit = ingredientInput.unit
                                )
                            }.filter { it.ingredientName.isNotBlank() }
                            recipeViewModel.insertIngredients(ingredientEntities)

                            val stepEntities = steps.map { stepInput ->
                                com.example.chefmate.database.entity.StepEntity(
                                    recipeId = recipeId,
                                    description = stepInput.description
                                )
                            }.filter { it.description.isNotBlank() }
                            recipeViewModel.insertSteps(stepEntities)

                            withContext(Dispatchers.Main) {
                                navController.popBackStack()
                            }
                        }
                    }
                },
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun AddEditRecipeScreenPreview() {
    AddEditRecipeScreen(-1, rememberNavController())
}