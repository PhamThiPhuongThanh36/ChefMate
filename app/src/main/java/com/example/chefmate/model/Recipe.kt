package com.example.chefmate.model

data class Recipe(
    val recipeId: Int? = null,
    val userId: Int? = null,
    val recipeName: String,
    val image: String,
    val cookingTime: String,
    val ration: Int,
    val viewCount: Int,
    val likeQuantity: Int,
    val isPublic: Boolean,
    val createdAt: String,
    val userName: String,
    val userImage: String,
    val isLiked: Boolean = false,
)