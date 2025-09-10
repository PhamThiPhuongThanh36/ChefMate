package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Int? = null,
    val recipeId: Int,
    val tagName: String
)