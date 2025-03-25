package com.example.homeworks.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun compressBitmap(bitmap: Bitmap, quality: Int = 80): Bitmap {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    val byteArray = stream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Context.compressUri(uri: Uri, quality: Int = 80): Bitmap? {
    return try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        originalBitmap?.let { compressBitmap(it, quality) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
