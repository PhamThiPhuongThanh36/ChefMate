package com.example.chefmate.model

import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity

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

data class RecipeView(
    val recipeId: Int? = null,
    val recipeName: String,
    val image: String,
    val cookingTime: String,
    val ration: Int,
    val ingredients: List<IngredientInput>,
    val steps: List<CookingStepAddRecipeData>,
    val viewCount: Int,
    val likeQuantity: Int,
    val isPublic: Boolean,
    val createdAt: String,
    val user: AnotherUserData,
    val isLiked: Boolean = false,
)

fun RecipeView.toEntities(): Triple<RecipeEntity, List<IngredientEntity>, List<StepEntity>> {
    val recipeEntity = RecipeEntity(
        recipeId = recipeId,
        recipeName = recipeName,
        image = image,
        cookingTime = cookingTime,
        ration = ration,
        viewCount = viewCount,
        likeQuantity = likeQuantity,
        isPublic = isPublic,
        createdAt = createdAt,
        userId = user.userId
    )

    val ingredientsEntities = ingredients.map {
        IngredientEntity(
            recipeId = recipeId,
            ingredientName = it.ingredientName,
            weight = it.weight,
            unit = it.unit
        )
    }

    val stepsEntities = steps.mapIndexed { index, step ->
        StepEntity(
            recipeId = recipeId ?: 0,
            description = step.content
        )
    }

    return Triple(recipeEntity, ingredientsEntities, stepsEntities)
}
