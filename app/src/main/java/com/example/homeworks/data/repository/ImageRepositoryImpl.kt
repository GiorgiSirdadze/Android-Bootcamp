package com.example.homeworks.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.homeworks.domain.repository.ImageRepository
import com.example.homeworks.domain.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ImageRepositoryImpl(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImageRepository {

    override suspend fun compressBitmap(bitmap: Bitmap, quality: Int): Resource<Bitmap> {
        return withContext(dispatcher) {
            try {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
                val byteArray = stream.toByteArray()
                val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                Resource.Success(compressedBitmap)
            } catch (e: Exception) {
                Resource.Error("Bitmap compression failed: ${e.message}")
            }
        }
    }

    override suspend fun compressImageFromUri(uri: Uri, quality: Int): Resource<Bitmap> {
        return withContext(dispatcher) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                    ?: return@withContext Resource.Error("Could not open input stream")

                inputStream.use { stream ->
                    val originalBitmap = BitmapFactory.decodeStream(stream)
                        ?: return@withContext Resource.Error("Could not decode bitmap")

                    when (val compressedResult = compressBitmap(originalBitmap, quality)) {
                        is Resource.Success -> compressedResult
                        is Resource.Error -> compressedResult
                        is Resource.Loader -> Resource.Error("Unexpected loader state")
                    }
                }
            } catch (e: Exception) {
                Resource.Error("URI compression failed: ${e.message}")
            }
        }
    }
}