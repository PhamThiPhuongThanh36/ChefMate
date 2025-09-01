package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefmate.database.entity.ShoppingEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM ShoppingLists")
    fun getAllShoppingLists(): Flow<List<ShoppingEntity>>

    @Query("SELECT * FROM ShoppingItems WHERE shoppingId = :shoppingId")
    fun getShoppingItemsById(shoppingId: Int): Flow<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(shoppingItems: List<ShoppingItemEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoppingItem(shoppingItem: ShoppingItemEntity)

    @Query("UPDATE ShoppingItems SET status = :status WHERE siId = :siId")
    suspend fun updateStatusShoppingItem(status: Boolean, siId: Int)

    @Query("UPDATE ShoppingLists SET status = :status WHERE shoppingId = :shoppingId")
    suspend fun updateStatusShoppingList(status: Boolean, shoppingId: Int)

    @Query("DELETE FROM ShoppingItems WHERE siId = :siId")
    suspend fun deleteShoppingItemById(siId: Int)
}