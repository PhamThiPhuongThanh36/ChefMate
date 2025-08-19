package com.example.chefmate.repository

import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val stepDao: StepDao
) {
    fun getAllRecipes() : Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    suspend fun insertRecipe(recipe: RecipeEntity): Long{
        return recipeDao.insertRecipe(recipe)
    }

    suspend fun getAllIngredientsByRecipeId(recipeId: Int): List<IngredientEntity> {
        return ingredientDao.getIngredientsByRecipeId(recipeId)
    }

    suspend fun insertIngredients(ingredients: List<IngredientEntity>) {
        ingredientDao.insertIngredients(ingredients)
    }

    suspend fun getAllStepsByRecipeId(recipeId: Int): List<StepEntity> {
        return stepDao.getStepsByRecipeId(recipeId)
    }

    suspend fun insertSteps(steps: List<StepEntity>) {
        return stepDao.insertSteps(steps)
    }
}