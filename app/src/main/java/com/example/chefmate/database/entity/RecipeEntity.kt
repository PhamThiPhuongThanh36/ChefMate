package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chefmate.model.Recipe

@Entity(tableName = "Recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int? = null,
    val userId: Int? = null,
    val recipeName: String,
    val image: String,
    val cookingTime: String,
    val ration: Int,
    val viewCount: Int,
    val likeQuantity: Int,
    val createdAt: String,
)

// Hàm mở rộng để chuyển đổi RecipeEntity sang Recipe
fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        recipeId = this.recipeId, // Map recipeId từ Entity sang id của Model
        userId = this.userId,
        recipeName = this.recipeName,
        image = this.image,
        cookingTime = this.cookingTime,
        ration = this.ration,
        viewCount = this.viewCount,
        likeQuantity = this.likeQuantity,
        createdAt = this.createdAt,
        userName = "",
        userImage = "",
        isLiked = true
    )
}