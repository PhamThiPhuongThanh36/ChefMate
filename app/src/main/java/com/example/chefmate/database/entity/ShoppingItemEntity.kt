package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingItems")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val siId: Int? = null,
    val shoppingId: Int? = null,
    val ingredientId: Int?= null,
    val status: Boolean
)