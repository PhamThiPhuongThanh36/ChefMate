package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefmate.database.entity.IngredientEntity

@Dao
interface IngredientDao {
    @Query("Select * from Ingredients where recipeId = :recipeId")
    fun getIngredientsByRecipeId(recipeId: Int): List<IngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<IngredientEntity>)

}