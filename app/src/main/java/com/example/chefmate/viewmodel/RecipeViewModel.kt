package com.example.chefmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.database.entity.StepEntity
import com.example.chefmate.database.entity.toRecipe
import com.example.chefmate.model.Recipe
import com.example.chefmate.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository): ViewModel() {
    val allRecipes: Flow<List<RecipeEntity>> = repository.getAllRecipes()
    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return repository.insertRecipe(recipe)
    }

    fun getRecipeById(recipeId: Int): Flow<Recipe?> {
        return repository.getRecipeById(recipeId)
            .map { entity -> entity?.toRecipe() } // chuyển từ RecipeEntity sang Recipe
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
}

