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

    @Multipart
    @POST("api/recipes/create")
    suspend fun createRecipe(
        @Part("recipeName") recipeName: RequestBody,
        @Part("cookingTime") cookingTime: RequestBody,
        @Part("ration") ration: RequestBody,
        @Part("ingredients") ingredients: RequestBody,
        @Part("cookingSteps") cookingSteps: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): CreateRecipeResponse
}
