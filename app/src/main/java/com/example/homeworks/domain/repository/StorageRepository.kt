package com.example.homeworks.domain.repository

import android.graphics.Bitmap
import com.example.homeworks.domain.resource.Resource

interface StorageRepository {
    suspend fun uploadImage(bitmap: Bitmap): Resource<String>
}