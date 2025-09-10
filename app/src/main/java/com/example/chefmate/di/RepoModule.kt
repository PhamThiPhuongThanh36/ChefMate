package com.example.chefmate.di

import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.ShoppingDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.dao.TagDao
import com.example.chefmate.repository.RecipeRepository
import com.example.chefmate.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        ingredientDao: IngredientDao,
        stepDao: StepDao,
        tagDao: TagDao
    ): RecipeRepository {
        return RecipeRepository(recipeDao, ingredientDao, stepDao, tagDao)
    }

    @Singleton
    @Provides
    fun provideShoppingRepository(
        shoppingDao: ShoppingDao,
        ingredientDao: IngredientDao,
    ): ShoppingRepository {
        return ShoppingRepository(shoppingDao, ingredientDao)
    }
}