package com.example.chefmate.repository

import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.ShoppingDao
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.ShoppingEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val ingredientDao: IngredientDao
) {
    fun getIngredientById(ingredientId: Int) = ingredientDao.getIngredientById(ingredientId)
    fun getIngredientsByRecipeId(recipeId: Int) = ingredientDao.getIngredientsByRecipeId(recipeId)
    fun getShoppingItemsById(shoppingId: Int) = shoppingDao.getShoppingItemsById(shoppingId)

    fun getAllShoppingLists() = shoppingDao.getAllShoppingLists()

    suspend fun updateIngredient(ingredient: IngredientEntity) {
        ingredientDao.updateIngredient(ingredient)
    }

    suspend fun insertIngredient(ingredient: IngredientEntity): Long {
        return ingredientDao.insertIngredient(ingredient)
    }
    suspend fun insertShoppingList(shoppingList: ShoppingEntity): Long{
        return shoppingDao.insertShoppingList(shoppingList)
    }

    suspend fun updateShoppingItem(shoppingItem: ShoppingItemEntity) {
        shoppingDao.updateShoppingItem(shoppingItem)
    }

    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    suspend fun insertShoppingItems(shoppingItems: List<ShoppingItemEntity>) {
        shoppingDao.insertShoppingItems(shoppingItems)
    }

    suspend fun updateStatusShoppingList(status: Boolean, shoppingId: Int) {
        shoppingDao.updateStatusShoppingList(status, shoppingId)
    }

    suspend fun updateStatusShoppingItem(status: Boolean, siId: Int) {
        shoppingDao.updateStatusShoppingItem(status, siId)
    }

    suspend fun deleteShoppingItemById(siId: Int) {
        shoppingDao.deleteShoppingItemById(siId)
    }
}