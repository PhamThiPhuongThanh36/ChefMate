package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefmate.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipe: RecipeEntity)
}