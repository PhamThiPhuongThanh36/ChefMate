package com.example.chefmate.ui.recipe

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.chefmate.R
import com.example.chefmate.api.ApiClient
import com.example.chefmate.common.AddTagDialog
import com.example.chefmate.common.CircularLoading
import com.example.chefmate.common.CustomButton
import com.example.chefmate.common.EditTextWithouthDescripe
import com.example.chefmate.common.Header
import com.example.chefmate.common.Label
import com.example.chefmate.common.TimeDropdown
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.TagEntity
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.model.CookingStepAddRecipeData
import com.example.chefmate.model.CreateRecipeData
import com.example.chefmate.model.IngredientItem
import com.example.chefmate.model.StepInput
import com.example.chefmate.model.TagData
import com.example.chefmate.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AddEditRecipeScreen(recipeId: Int, navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    var recipeName by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var tags = remember { mutableStateListOf<String>() }
    var cookingTime by remember { mutableStateOf("") }
    var ration by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Phút") }
    val ingredients = remember { mutableStateListOf<IngredientEntity>() }
    val steps = remember {
        mutableStateListOf(StepInput( ""))
    }
    var isPublic by remember { mutableStateOf(false) }
    var isShowAddTags by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val focusManager = LocalFocusManager.current
    val ingredientFocusRequesters = remember { mutableStateListOf<FocusRequester>() }

    LaunchedEffect(ingredients.size, steps.size) {
        while (ingredientFocusRequesters.size < ingredients.size * 3) {
            ingredientFocusRequesters.add(FocusRequester())
        }
        while (ingredientFocusRequesters.size > ingredients.size * 3) {
            ingredientFocusRequesters.removeAt(ingredientFocusRequesters.size - 1)
        }
    }
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

    LaunchedEffect(recipeId) {
        val recipe = recipeViewModel.getRecipeById(recipeId).first()
        recipe?.let {
            recipeName = recipe.recipeName
            imageUri = recipe.image.toUri()
            cookingTime = recipe.cookingTime
            ration = recipe.ration.toString()
            selectedUnit = "Phút"
        }

        isPublic = recipe?.isPublic ?: false

        val tagEntities = recipeViewModel.getTagsByRecipeId(recipeId).first()
        tags.clear()
        tags.addAll(tagEntities.map { it.tagName })

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
                    .clickable { launcher.launch(arrayOf("image/*")) }
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                ),
                border = BorderStroke(1.dp, Color(0xFFA3A3A3)),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(top = 15.dp)
                    .height(56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Thêm tag",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFFFF9800),
                        modifier = Modifier
                            .clickable {
                                isShowAddTags = true
                            }
                    )
                    tags.forEach { tag ->
                        Text(
                            text = tag,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(Color(0xFFFFC107), shape = RoundedCornerShape(3.dp))
                                .padding(2.dp)
                        )
                    }
                }
            }
            EditTextWithouthDescripe(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = "Tên công thức",
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
            val selectedOption = if (isPublic) remember { mutableStateOf(options[1]) } else remember { mutableStateOf(options[0]) }
            LaunchedEffect(selectedOption.value) {
                isPublic = selectedOption.value == options[1]
            }
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
                            selected = (option == selectedOption.value),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFF97518),
                                unselectedColor = Color(0xFFF97518)
                            ),
                            onClick = { selectedOption.value = option }
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
                    val nameIndex = index * 3
                    val weightIndex = index * 3 + 1
                    val unitIndex = index * 3 + 2
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF),
                        ),
                        modifier = Modifier
                            .padding(3.dp)
                            .border(1.dp, Color(0xFFA3A3A3), RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                            ) {
                                TextField(
                                    value = item.ingredientName,
                                    onValueChange = { ingredients[index] = item.copy(ingredientName = it) },
                                    singleLine = true,
                                    label = {
                                        Text(text = "Tên nguyên liệu")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            ingredientFocusRequesters[weightIndex].requestFocus()
                                        }
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFFFFFFF),
                                        unfocusedContainerColor = Color(0xFFFFFFFF),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .focusRequester(
                                            ingredientFocusRequesters.getOrNull(
                                                nameIndex
                                            ) ?: FocusRequester()
                                        )
                                        .weight(1f)
                                )
                                if (ingredients.size > 1) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_close),
                                        contentDescription = null,
                                        tint = Color(0xFFFF9800),
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(24.dp)
                                            .clickable {
                                                ingredients.removeAt(index)
                                            }
                                    )
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                TextField(
                                    value = item.weight,
                                    onValueChange = { ingredients[index] = item.copy(weight = it) },
                                    label = {
                                        Text(text = "Khối lượng")
                                    },
                                    singleLine = true,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFFFFFFF),
                                        unfocusedContainerColor = Color(0xFFFFFFFF),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            ingredientFocusRequesters.getOrNull(unitIndex)?.requestFocus()
                                        }
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .focusRequester(
                                            ingredientFocusRequesters.getOrNull(
                                                weightIndex
                                            ) ?: FocusRequester()
                                        )
                                )
                                TextField(
                                    value = item.unit,
                                    onValueChange = { ingredients[index] = item.copy(unit = it) },
                                    singleLine = true,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFFFFFFF),
                                        unfocusedContainerColor = Color(0xFFFFFFFF),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = { focusManager.clearFocus() }
                                    ),
                                    label = {
                                        Text(
                                            text = "Đơn vị"
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .focusRequester(
                                            ingredientFocusRequesters.getOrNull(
                                                unitIndex
                                            ) ?: FocusRequester()
                                        )
                                )
                            }
                        }
                    }
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
                    .fillMaxWidth(0.9f)
            ) {
                Label("Các bước nấu")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            ) {
                steps.forEachIndexed { index, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        EditTextWithouthDescripe(
                            value = item.description,
                            onValueChange = { steps[index] = item.copy(description = it) },
                            label = "Bước ${index + 1}",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                        )
                        if (steps.size > 1) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        steps.removeAt(index)
                                    }
                            )
                        }
                    }
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
                    coroutineScope.launch(Dispatchers.IO) {
                        val userId = DataStoreHelper.getUserData(context).userId
                        isLoading = true
                        if (recipeId == -1) {
                            if (isPublic == false) {
                                val newRecipe = RecipeEntity(
                                    userId = userId,
                                    recipeName = recipeName,
                                    image = imageUri.toString(),
                                    cookingTime = cookingTime,
                                    ration = ration.toInt(),
                                    viewCount = 0,
                                    likeQuantity = 0,
                                    isPublic = if (selectedOption.value == "Riêng tư") false else true,
                                    createdAt = currentDateTime
                                )
                                val newRecipeId = recipeViewModel.insertRecipe(newRecipe).toInt()

                                val tagEntities = tags.map { tag ->
                                    TagEntity(
                                        recipeId = newRecipeId,
                                        tagName = tag
                                    )
                                }.filter { it.tagName.isNotBlank() }
                                recipeViewModel.insertTags(tagEntities)

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
                                val listIngredients = ingredients.map {
                                    IngredientItem(
                                        it.ingredientId,
                                        it.ingredientName,
                                        it.weight.toInt(),
                                        it.unit
                                    )
                                }
                                val listSteps = steps.map {
                                    CookingStepAddRecipeData(it.description)
                                }
                                val listTags = tags.map { TagData(it) }
                                val recipe = CreateRecipeData(
                                    recipeName,
                                    cookingTime,
                                    ration.toInt(),
                                    listIngredients,
                                    listSteps,
                                    userId,
                                    imageUri.toString(),
                                    listTags,
                                )
                                val response = ApiClient.createRecipe(
                                    context = context,
                                    recipe = recipe
                                )
                                if (response != null) {
                                    if (response.success) {
                                        val newRecipe = RecipeEntity(
                                            userId = userId,
                                            recipeName = recipeName,
                                            image = imageUri.toString(),
                                            cookingTime = cookingTime,
                                            ration = ration.toInt(),
                                            viewCount = 0,
                                            likeQuantity = 0,
                                            isPublic = if (selectedOption.value == "Riêng tư") false else true,
                                            createdAt = currentDateTime
                                        )
                                        val newRecipeId = recipeViewModel.insertRecipe(newRecipe).toInt()

                                        val tagEntities = tags.map { tag ->
                                            TagEntity(
                                                recipeId = newRecipeId,
                                                tagName = tag
                                            )
                                        }.filter { it.tagName.isNotBlank() }
                                        recipeViewModel.insertTags(tagEntities)

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
                                    }
                                }
                            }
                        } else {
                            val newRecipe = RecipeEntity(
                                recipeId = recipeId,
                                userId = 1,
                                recipeName = recipeName,
                                image = imageUri.toString(),
                                cookingTime = cookingTime,
                                ration = ration.toInt(),
                                viewCount = 0,
                                likeQuantity = 0,
                                isPublic = if (selectedOption.value == "Riêng tư") false else true,
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
                            recipeViewModel.deleteIngredientsByRecipeId(recipeId)
                            recipeViewModel.insertIngredients(ingredientEntities)

                            val stepEntities = steps.map { stepInput ->
                                com.example.chefmate.database.entity.StepEntity(
                                    recipeId = recipeId,
                                    description = stepInput.description
                                )
                            }.filter { it.description.isNotBlank() }
                            recipeViewModel.deleteStepsByRecipeId(recipeId)
                            recipeViewModel.insertSteps(stepEntities)

                            val tagEntities = tags.map { tag ->
                                TagEntity(
                                    recipeId = recipeId,
                                    tagName = tag
                                )
                            }
                            recipeViewModel.deleteTagsByRecipeId(recipeId)
                            recipeViewModel.insertTags(tagEntities)

                            withContext(Dispatchers.Main) {
                                navController.popBackStack()
                            }
                        }
                    }
                    isLoading = false
                },
                modifier = Modifier
            )
            if (isShowAddTags) {
                AddTagDialog(
                    tag = tag,
                    onTagChange = { tag = it },
                    onDismiss = { isShowAddTags = false },
                    listTags = tags,
                    onAddTag = {
                        tags.add(tag)
                        tag = ""
                    }
                )
            }
            if (isLoading) {
                CircularLoading()
            }
        }
    }
}

@Preview
@Composable
fun AddEditRecipeScreenPreview() {
    AddEditRecipeScreen(-1, rememberNavController())
}