package com.example.homeworks.data.repository

import android.graphics.Bitmap
import com.example.homeworks.domain.repository.StorageRepository
import com.example.homeworks.domain.resource.Resource
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {

    override suspend fun uploadImage(bitmap: Bitmap): Resource<String> =
        withContext(Dispatchers.IO) {
            try {
                val imageData = ByteArrayOutputStream().use { baos ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos)
                    baos.toByteArray()
                }

                val imageRef = storage.reference.child(
                    "images/${System.currentTimeMillis()}_${(0..1000).random()}.jpg"
                )

                val metadata = storageMetadata {
                    contentType = "image/jpeg"
                    setCustomMetadata("uploaded_by", "mobile_app")
                }

                imageRef.putBytes(imageData, metadata).await()
                val downloadUrl = imageRef.downloadUrl.await().toString()

                Resource.Success(downloadUrl)
            } catch (e: StorageException) {
                Resource.Error(
                    errorMessage = "Storage error: ${e.message ?: "Check your internet connection"}"
                )
            } catch (e: Exception) {
                Resource.Error(
                    errorMessage = "Upload failed: ${e.localizedMessage ?: "Unknown error"}"
                )
            }
        }
}