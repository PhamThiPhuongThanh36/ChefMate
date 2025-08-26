package com.example.chefmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.chefmate.database.entity.ShoppingEntity
import com.example.chefmate.database.entity.ShoppingItemEntity

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoppingList(shoppingList: ShoppingEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoppingItem(shoppingItem: ShoppingItemEntity)
}