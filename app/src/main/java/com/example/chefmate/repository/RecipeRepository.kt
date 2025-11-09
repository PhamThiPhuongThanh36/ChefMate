package com.example.chefmate.repository

import android.util.Log
import com.example.chefmate.api.ApiClient
import com.example.chefmate.api.ApiConstant
import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.dao.TagDao
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import com.example.chefmate.database.entity.TagEntity
import com.example.chefmate.database.entity.toRecipeView
import com.example.chefmate.model.RecipeView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class   RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val stepDao: StepDao,
    private val tagDao: TagDao
) {
    val api = ApiClient.createService(ApiConstant.MAIN_URL)

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

    suspend fun getRecipeByIdLocal(recipeId: Int): RecipeView? {
        val recipeEntity = recipeDao.getRecipeWithDetails(recipeId)
        return recipeEntity?.toRecipeView()
    }

    suspend fun getRecipeByIdServer(recipeId: Int): RecipeView? {
        return try {
            val response = api.getRecipeById(recipeId)
            Log.d("RecipeRepository", response.data.toString())
            if (response.success && response.data != null) response.data else null
        } catch (e: Exception) {
            null
        }
    }

}