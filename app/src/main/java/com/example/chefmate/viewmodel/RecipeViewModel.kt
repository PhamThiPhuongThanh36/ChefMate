package com.example.chefmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chefmate.api.ApiClient
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import com.example.chefmate.database.entity.TagEntity
import com.example.chefmate.database.entity.toRecipe
import com.example.chefmate.model.Recipe
import com.example.chefmate.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository): ViewModel() {
    private val _topTrending = MutableStateFlow<List<Recipe>>(emptyList())
    val topTrending: StateFlow<List<Recipe>> = _topTrending
    val allRecipes: Flow<List<RecipeEntity>> = repository.getAllRecipes()
    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return repository.insertRecipe(recipe)
    }

    fun getRecipeById(recipeId: Int): Flow<Recipe?> {
        return repository.getRecipeById(recipeId)
            .map { entity -> entity?.toRecipe() }
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

    suspend fun deleteRecipeById(recipeId: Int) {
        viewModelScope.launch {
            repository.deleteRecipeById(recipeId)
        }
    }

    suspend fun updateRecipe(recipe: RecipeEntity) {
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

    suspend fun getTopTrending(userId: Int? = null) {
        val response = ApiClient.getTopTrending(userId)
        response?.let {
            response.data?.let {
                _topTrending.value = it
            }
        }
    }
}

