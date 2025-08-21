package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chefmate.model.IngredientInput

@Entity(tableName = "Ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int? = null,
    val recipeId: Int? = null,
    val ingredientName: String,
    val weight: Double,
    val unit: String
)

fun IngredientEntity.toIngredient(): IngredientInput {
    return IngredientInput(
        ingredientName = this.ingredientName,
        weight = this.weight.toString(),
        unit = this.unit
    )
}

