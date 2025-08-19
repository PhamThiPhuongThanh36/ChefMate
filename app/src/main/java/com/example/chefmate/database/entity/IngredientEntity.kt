package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int? = null,
    val recipeId: Int? = null,
    val ingredientName: String,
    val weight: Double,
    val unit: String
)

