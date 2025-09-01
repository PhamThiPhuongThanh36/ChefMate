package com.example.chefmate.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chefmate.database.entity.IngredientEntity
import com.example.chefmate.database.entity.ShoppingEntity
import com.example.chefmate.database.entity.ShoppingItemEntity
import com.example.chefmate.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repository: ShoppingRepository) : ViewModel() {


    val allShoppingLists = repository.getAllShoppingLists()

    fun getShoppingItemsById(shoppingId: Int) = repository.getShoppingItemsById(shoppingId)

    fun getIngredientById(ingredientId: Int) = repository.getIngredientById(ingredientId)
    fun getIngredientsByRecipeId(recipeId: Int) = repository.getIngredientsByRecipeId(recipeId)
    suspend fun updateIngredient(ingredient: IngredientEntity) {
        repository.updateIngredient(ingredient)
    }
    suspend fun insertShoppingList(shoppingList: ShoppingEntity): Long {
        val id = repository.insertShoppingList(shoppingList)
        return id
    }

    suspend fun insertIngredient(ingredient: IngredientEntity): Long {
        return repository.insertIngredient(ingredient)
    }

    suspend fun insertShoppingItems(shoppingItems: List<ShoppingItemEntity>) {
        repository.insertShoppingItems(shoppingItems)
    }

    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity) {
        repository.insertShoppingItem(shoppingItem)
    }

    suspend fun updateStatusShoppingList(status: Boolean, shoppingId: Int) {
        repository.updateStatusShoppingList(status, shoppingId)
    }

    suspend fun updateStatusShoppingItem(status: Boolean, siId: Int) {
        repository.updateStatusShoppingItem(status, siId)
    }

    suspend fun deleteShoppingItemById(siId: Int) {
        repository.deleteShoppingItemById(siId)
    }

    suspend fun updateShoppingItem(shoppingItem: ShoppingItemEntity) {
        repository.updateShoppingItem(shoppingItem)
    }

}
