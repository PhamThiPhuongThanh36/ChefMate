package com.example.chefmate.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.example.chefmate.model.ApiResponse
import com.example.chefmate.model.UserRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

suspend fun updateProfileRetrofit(
    context: Context,
    api: ApiService,
    user: UserRequest,
): ApiResponse<Unit>? {
    val imageUri = user.image.toUri()

    val fileName = "avatar_${System.currentTimeMillis()}.jpg"
    val file = File(context.cacheDir, fileName)

    try {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }

        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val fullName = user.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = user.phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = user.email.toRequestBody("text/plain".toMediaTypeOrNull())

        return api.updateProfile(
            userId = user.userId,
            fullName,
            phone,
            email,
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