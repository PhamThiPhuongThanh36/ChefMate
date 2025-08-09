package com.example.chefmate.model

data class Step(
    val stepId: Int,
    val recipeId: Int,
    val description: String
)

data class StepInput(
    val index: Int,
    val description: String
)
