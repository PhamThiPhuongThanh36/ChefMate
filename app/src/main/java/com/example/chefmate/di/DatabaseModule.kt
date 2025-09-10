package com.example.chefmate.di

import android.content.Context
import androidx.room.Room
import com.example.chefmate.database.AppDatabase
import com.example.chefmate.database.dao.IngredientDao
import com.example.chefmate.database.dao.RecipeDao
import com.example.chefmate.database.dao.ShoppingDao
import com.example.chefmate.database.dao.StepDao
import com.example.chefmate.database.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chefmate_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

    @Singleton
    @Provides
    fun provideIngredientDao(db: AppDatabase): IngredientDao = db.ingredientDao()

    @Singleton
    @Provides
    fun provideStepDao(db: AppDatabase): StepDao = db.stepDao()

    @Singleton
    @Provides
    fun provideShoppingDao(db: AppDatabase): ShoppingDao = db.shoppingDao()

    @Singleton
    @Provides
    fun provideTagDao(db: AppDatabase): TagDao = db.tagDao()
}
