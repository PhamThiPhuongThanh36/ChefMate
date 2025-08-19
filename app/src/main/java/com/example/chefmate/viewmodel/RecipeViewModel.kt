package com.example.chefmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.model.Recipe
import com.example.chefmate.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeViewModel(private val repository: RecipeRepository): ViewModel() {
    val allRecipes: Flow<List<RecipeEntity>> = repository.getAllRecipes()
    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return repository.insertRecipe(recipe)
    }
}

class RecipeViewModelFactory(private val recipeRepository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(recipeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
