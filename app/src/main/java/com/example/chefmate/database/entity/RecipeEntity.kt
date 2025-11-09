package com.example.chefmate.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.chefmate.model.CookingStepAddRecipeData
import com.example.chefmate.model.IngredientInput
import com.example.chefmate.model.Recipe
import com.example.chefmate.model.RecipeView

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
    val isPublic: Boolean,
    val createdAt: String,
)

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        recipeId = this.recipeId,
        userId = this.userId,
        recipeName = this.recipeName,
        image = this.image,
        cookingTime = this.cookingTime,
        ration = this.ration,
        viewCount = this.viewCount,
        likeQuantity = this.likeQuantity,
        isPublic = this.isPublic,
        createdAt = this.createdAt,
        userName = "phuongthanh",
        userImage = "",
        isLiked = true
    )
}

data class RecipeWithDetails(
    @Embedded val recipe: RecipeEntity,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val steps: List<StepEntity>,

    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val user: UserEntity?
)

fun RecipeWithDetails.toRecipeView(): RecipeView {
    return RecipeView(
        recipeId = recipe.recipeId,
        recipeName = recipe.recipeName,
        image = recipe.image,
        cookingTime = recipe.cookingTime,
        ration = recipe.ration,
        ingredients = ingredients.map {
            IngredientInput(
                ingredientName = it.ingredientName,
                weight = it.weight,
                unit = it.unit
            )
        },
        steps = steps.map {
            CookingStepAddRecipeData(
                indexStep = it.stepId ?: 0,
                content = it.description
            )
        },
        viewCount = recipe.viewCount,
        likeQuantity = recipe.likeQuantity,
        isPublic = recipe.isPublic,
        createdAt = recipe.createdAt,
        user = user!!.toUserView(),
        isLiked = true
    )
}
