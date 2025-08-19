package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefmate.database.entity.StepEntity

@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<StepEntity>)

    @Query("SELECT * FROM Steps WHERE recipeId = :recipeId")
    suspend fun getStepsByRecipeId(recipeId: Int): List<StepEntity>

}