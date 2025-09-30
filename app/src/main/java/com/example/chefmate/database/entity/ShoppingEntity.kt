package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingLists")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    val shoppingId: Int? = null,
    val status: Boolean,
    val createdAt: String,
)