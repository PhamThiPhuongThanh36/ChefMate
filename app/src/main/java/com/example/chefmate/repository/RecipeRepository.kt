package com.example.chefmate.repository

import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.dao.TagDao
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import com.example.chefmate.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class   RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val stepDao: StepDao,
    private val tagDao: TagDao
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

    suspend fun updateRecipe(recipe: RecipeEntity) {
        return recipeDao.updateRecipe(recipe)
    }

    fun getTagsByRecipeId(recipeId: Int): Flow<List<TagEntity>> {
        return tagDao.getTagsByRecipeId(recipeId)
    }

    suspend fun insertTags(tagRecipes: List<TagEntity>) {
        return tagDao.insertTags(tagRecipes)
    }

    fun deleteTagsByRecipeId(recipeId: Int) {
        return tagDao.deleteTagsByRecipeId(recipeId)
    }

    suspend fun deleteIngredientsByRecipeId(recipeId: Int) {
        return ingredientDao.deleteIngredientById(recipeId)
    }

    suspend fun deleteStepsByRecipeId(recipeId: Int) {
        return stepDao.deleteStepsByRecipeId(recipeId)

    }

    fun searchRecipes(query: String): Flow<List<RecipeEntity>> {
        return recipeDao.searchRecipes(query)
    }

}