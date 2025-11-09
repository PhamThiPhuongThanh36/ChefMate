package com.example.chefmate.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.example.chefmate.model.CreateRecipeData
import com.example.chefmate.model.CreateRecipeResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

suspend fun createRecipeRetrofit(
    context: Context,
    api: ApiService,
    recipe: CreateRecipeData
): CreateRecipeResponse? {
    val imageUri = recipe.image.toUri()
    if (imageUri == Uri.EMPTY || imageUri.toString().isBlank()) {
        Log.e("CreateRecipe", "Không có ảnh")
        return null
    }

    val fileName = "recipe_${System.currentTimeMillis()}.jpg"
    val file = File(context.cacheDir, fileName)

    try {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }

        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }

        if (!file.exists() || file.length() == 0L) {
            Log.e("CreateRecipe", "File không tồn tại hoặc rỗng")
            return null
        }

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val recipeName = recipe.recipeName.toRequestBody("text/plain".toMediaTypeOrNull())
        val cookingTime = recipe.cookingTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val ration = recipe.ration.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredients = Gson().toJson(recipe.ingredients).toRequestBody("text/plain".toMediaTypeOrNull())
        val steps = Gson().toJson(recipe.steps).toRequestBody("text/plain".toMediaTypeOrNull())
        val isPublic = recipe.isPublic.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val userId = recipe.userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        return api.createRecipe(
            recipeName,
            cookingTime,
            ration,
            ingredients,
            steps,
            isPublic,
            userId,
            imagePart
        )
    } catch (e: Exception) {
        Log.e("CreateRecipe", "Lỗi: ${e.message}", e)
        return null
    } finally {
        if (file.exists()) {
            file.delete()
        }
    }
}