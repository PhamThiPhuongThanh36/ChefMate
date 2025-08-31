package com.example.chefmate.repository

import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
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

    fun getAllIngredientsByRecipeId(recipeId: Int): Flow<List<IngredientEntity>> {
        return ingredientDao.getIngredientsByRecipeId(recipeId)
    }

    suspend fun insertIngredients(ingredients: List<IngredientEntity>) {
        ingredientDao.insertIngredients(ingredients)
    }

    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?> {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getAllStepsByRecipeId(recipeId: Int): Flow<List<StepEntity>> {
        return stepDao.getStepsByRecipeId(recipeId)
    }

    suspend fun insertSteps(steps: List<StepEntity>) {
        return stepDao.insertSteps(steps)
    }

    suspend fun deleteRecipeById(recipeId: Int) {
        return recipeDao.deleteRecipeById(recipeId)
    }
}