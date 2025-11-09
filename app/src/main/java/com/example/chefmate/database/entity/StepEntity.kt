package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Steps")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val stepId: Int? = null,
    val recipeId: Int? = null,
    val description: String
)

