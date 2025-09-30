package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefmate.database.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query("Select * from Ingredients where recipeId = :recipeId")
    fun getIngredientsByRecipeId(recipeId: Int): Flow<List<IngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: IngredientEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Query("Select * from Ingredients where ingredientId = :ingredientId")
    fun getIngredientById(ingredientId: Int): Flow<IngredientEntity>

    @Query("Delete from Ingredients where recipeId = :recipeId")
    suspend fun deleteIngredientById(recipeId: Int)

}