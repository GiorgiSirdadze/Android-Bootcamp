package com.example.homeworks.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.homeworks.domain.resource.Resource

interface ImageRepository {
    suspend fun compressBitmap(bitmap: Bitmap, quality: Int = 80): Resource<Bitmap>
    suspend fun compressImageFromUri(uri: Uri, quality: Int = 80): Resource<Bitmap>
}