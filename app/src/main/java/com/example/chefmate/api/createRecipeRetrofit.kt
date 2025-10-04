package com.example.chefmate.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.chefmate.helper.CommonHelper
import com.example.chefmate.model.CreateRecipeData
import com.example.chefmate.model.CreateRecipeResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

suspend fun createRecipeRetrofit(context: Context, api: ApiService, recipe: CreateRecipeData): CreateRecipeResponse {
    // convert URI -> Bitmap
    val bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, recipe.image.toUri())
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, recipe.image.toUri())
        ImageDecoder.decodeBitmap(source)
    }

    // bitmap -> file (tạm thời trong cache dir)
    val file = File(context.cacheDir, "${CommonHelper.parseName(recipe.recipeName)}.jpg")
    val outputStream = file.outputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()

    // tạo MultipartBody.Part
    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

    // tạo các part khác
    val recipeName = recipe.recipeName.toRequestBody("text/plain".toMediaTypeOrNull())
    val cookingTime = recipe.cookingTime.toRequestBody("text/plain".toMediaTypeOrNull())
    val ration = recipe.ration.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    val ingredients = Gson().toJson(recipe.ingredients).toRequestBody("application/json".toMediaTypeOrNull())
    val cookingSteps = Gson().toJson(recipe.cookingSteps).toRequestBody("application/json".toMediaTypeOrNull())
    val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())

    return api.createRecipe(
        recipeName,
        cookingTime,
        ration,
        ingredients,
        cookingSteps,
        userId,
        imagePart
    )
}
