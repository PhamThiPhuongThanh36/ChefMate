package com.example.chefmate.model

import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.TagEntity

data class CreateRecipeData(
    val recipeName: String,
    val cookingTime: String,
    val ration: Int,
    val ingredients: List<IngredientItem>,
    val cookingSteps: List<CookingStepAddRecipeData>,
    val userId: Int,
    val image: String,
    val tags: List<TagData>
)

data class TagData(
    val tagName: String
)

data class IngredientItem(
    val ingredientId: Int? = null,
    val ingredientName: String,
    val weight: Int,
    val unit: String,
)

data class CookingStepAddRecipeData(
    val content: String,
)

data class RegisterRequest(
    val fullName: String,
    val phone: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val data: UserData? = null,
    val message: String? = null
)

data class UserData(
    val userId: Int,
    val fullName: String,
    val phone: String,
    val email: String,
    val passwordHash: String? = null,
    val followCount: Int,
    val recipeCount: Int,
    val createdAt: String
)

data class CreateRecipeResponse(
    val success: Boolean,
    val data: Int? = null,
    val message: String? = null
)
