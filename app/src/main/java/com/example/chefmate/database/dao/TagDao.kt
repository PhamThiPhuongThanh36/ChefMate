package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefmate.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTag(tag: TagEntity): Long

    @Query("SELECT * FROM Tags WHERE recipeId = :recipeId")
    fun getTagsByRecipeId(recipeId: Int): Flow<List<TagEntity>>

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun updateTagRecipes(tags: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>)

}