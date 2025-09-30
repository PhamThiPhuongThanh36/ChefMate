package com.example.chefmate.api

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.example.chefmate.helper.CommonHelper
import com.example.chefmate.model.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object ApiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    @SuppressLint("MemberExtensionConflict")
    suspend fun register(fullName: String, phone: String, email: String, password: String): LoginResponse? {
        val registerRequest = RegisterRequest(fullName, phone, email, password)
        val json = gson.toJson(registerRequest)

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(ApiConstant.REGISTER_URL)
            .post(requestBody)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        responseBody?.let {
                            gson.fromJson(it, LoginResponse::class.java)
                        }
                    } else {
                        Log.e(TAG, "Error: ${response.code}")
                        null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } catch (e: TimeoutException) {
                e.printStackTrace()
                null
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                null
            }
        }
    }

    @SuppressLint("MemberExtensionConflict")
    suspend fun login(identifier: String, password: String): LoginResponse? {
        val loginRequest = LoginRequest(identifier, password)
        val json = gson.toJson(loginRequest)
        Log.d("Login", json.toString())

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(ApiConstant.LOGIN_URL)
            .post(requestBody)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    Log.d(TAG, "login response code: ${response.code}")
                    when (response.code) {
                        in 200..299 -> {
                            val responseBody = response.body?.string()
                            responseBody?.let {
                                gson.fromJson(it, LoginResponse::class.java)
                            }
                        }
                        401 -> {
                            Log.e(TAG, "SignIn Unauthorized: ${response.message}")
                            LoginResponse(success = false, data = null, message = "Unauthorized access")
                        }
                        else -> {
                            Log.e(TAG, "SignIn Error: ${response.code} - ${response.message}")
                            null
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } catch (e: TimeoutException) {
                e.printStackTrace()
                null
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                null
            }
        }
    }

    @SuppressLint("MemberExtensionConflict")
    suspend fun createRecipe(context: Context, recipe: CreateRecipeData): CreateRecipeResponse? {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, recipe.image.toUri())
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, recipe.image.toUri())
            ImageDecoder.decodeBitmap(source)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val imageRequestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
        val ingredientsRequestBody = gson.toJson(recipe.ingredients)
        val cookingSteps = gson.toJson(recipe.cookingSteps)
        Log.d(TAG, "Ingredients Request Body: $ingredientsRequestBody")
        Log.d(TAG, "Cooking Steps: $cookingSteps")

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("recipeName", recipe.recipeName)
            .addFormDataPart("cookingTime", recipe.cookingTime)
            .addFormDataPart("ration", "${recipe.ration}")
            .addFormDataPart("ingredients", ingredientsRequestBody)
            .addFormDataPart("cookingSteps", cookingSteps)
            .addFormDataPart("userId", "1")
            .addFormDataPart("image", "${CommonHelper.parseName(recipe.recipeName)}.jpg", imageRequestBody)
            .build()

        val request = Request.Builder()
            .url(ApiConstant.CREATE_RECIPE_URL)
            .post(multipartBody)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        responseBody?.let {
                            gson.fromJson(it, CreateRecipeResponse::class.java)
                        }
                    } else {
                        Log.e(TAG, "Error: ${response.code}")
                        null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } catch (e: TimeoutException) {
                e.printStackTrace()
                null
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                null
            }
        }
    }

    private const val TAG = "ApiClient"
}