package com.example.chefmate.model

import androidx.compose.ui.text.font.FontWeight

data class Ingredient(
    val ingredientId: Int,
    val recipeId: Int,
    val ingredientName: String,
    val weight: Double,
    val unit: String
)

data class  IngredientInput(
    val ingredientName: String,
    val weight: String,
    val unit: String
)
