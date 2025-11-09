package com.example.chefmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefmate.api.ApiClient
import com.example.chefmate.api.ApiConstant
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import com.example.chefmate.database.entity.TagEntity
import com.example.chefmate.model.Recipe
import com.example.chefmate.model.RecipeView
import com.example.chefmate.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    val api = ApiClient.createService(ApiConstant.MAIN_URL)

    val allRecipes: Flow<List<RecipeEntity>> = repository.getAllRecipes()
    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return repository.insertRecipe(recipe)
    }

    suspend fun insertIngredients(ingredients: List<IngredientEntity>) {
        repository.insertIngredients(ingredients)
    }

    suspend fun insertSteps(steps: List<StepEntity>) {
        repository.insertSteps(steps)
    }

    fun getIngredientsByRecipeId(recipeId: Int): Flow<List<IngredientEntity>> {
        return repository.getAllIngredientsByRecipeId(recipeId)
    }

    fun getStepsByRecipeId(recipeId: Int): Flow<List<StepEntity>> {
        return repository.getAllStepsByRecipeId(recipeId)
    }

    fun deleteRecipeById(recipeId: Int) {
        viewModelScope.launch {
            repository.deleteRecipeById(recipeId)
        }
    }

    fun updateRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
        }
    }

    suspend fun insertTags(tags: List<TagEntity>) {
        repository.insertTags(tags)
    }

    fun getTagsByRecipeId(recipeId: Int): Flow<List<TagEntity>> {
        return repository.getTagsByRecipeId(recipeId)
    }

    suspend fun deleteTagsByRecipeId(recipeId: Int) {
        repository.deleteTagsByRecipeId(recipeId)
    }

    suspend fun deleteIngredientsByRecipeId(recipeId: Int) {
        repository.deleteIngredientsByRecipeId(recipeId)
    }

    suspend fun deleteStepsByRecipeId(recipeId: Int) {
        repository.deleteStepsByRecipeId(recipeId)
    }

    fun searchRecipes(query: String): Flow<List<RecipeEntity>> {
        return repository.searchRecipes(query)
    }

    suspend fun getTopTrending(): List<Recipe> {
        return api.getTopTrending().map { r ->
            r.copy(
                isPublic = r.isPublic ?: true,
                userName = r.userName ?: "",
                userImage = r.userImage ?: "",
                isLiked = r.isLiked ?: false
            )
        }
    }

    suspend fun getRecipeById(recipeId: Int): RecipeView? {
        val localRecipe = repository.getRecipeByIdLocal(recipeId)
        if (localRecipe != null) {
            return localRecipe
        }
        val remoteRecipe = repository.getRecipeByIdServer(recipeId)
        return remoteRecipe
    }

}
