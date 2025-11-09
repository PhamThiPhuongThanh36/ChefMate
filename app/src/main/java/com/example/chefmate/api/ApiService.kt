package com.example.chefmate.api

import com.example.chefmate.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/recipes/insertCollection")
    suspend fun insertCollection(@Body request: CollectionRequest): ApiResponse<Unit>

    @POST("api/recipes/getCollection")
    suspend fun getCollection(@Body request: GetCollectionRequest): ApiResponse<List<Recipe>>

    @GET("api/recipes/trending")
    suspend fun getTopTrending(): List<Recipe>

    @GET("api/recipes/recipe")
    suspend fun getRecipeById(@Query("recipeId") recipeId: Int): ApiResponse<RecipeView>

    @Multipart
    @PUT("api/users/{userId}/profile")
    suspend fun updateProfile(
        @Path("userId") userId: Int,
        @Part("fullName") fullName: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): ApiResponse<Unit>

    @Multipart
    @POST("api/recipes/create")
    suspend fun createRecipe(
        @Part("recipeName") recipeName: RequestBody,
        @Part("cookingTime") cookingTime: RequestBody,
        @Part("ration") ration: RequestBody,
        @Part("ingredients") ingredients: RequestBody,
        @Part("steps") steps: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): CreateRecipeResponse
}
