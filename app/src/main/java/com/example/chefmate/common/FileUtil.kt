package com.example.chefmate.common

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "IMG_${timeStamp}.jpg"
    val appDir = File(context.filesDir, "recipe_images") // Thư mục con để lưu ảnh công thức
    if (!appDir.exists()) {
        appDir.mkdirs() // Tạo thư mục nếu nó chưa tồn tại
    }
    val imageFile = File(appDir, fileName)

    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(imageFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return imageFile.absolutePath // Trả về đường dẫn tuyệt đối của ảnh đã lưu
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}