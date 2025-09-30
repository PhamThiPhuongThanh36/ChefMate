package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefmate.database.entity.RecipeEntity
import com.example.chefmate.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM Recipes WHERE recipeId = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

    @Query("SELECT * FROM Recipes WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?>

    @Query("SELECT * FROM Recipes WHERE recipeName LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>

}