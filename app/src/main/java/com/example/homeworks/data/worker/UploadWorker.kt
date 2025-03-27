package com.example.homeworks.data.worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.homeworks.domain.repository.StorageRepository
import com.example.homeworks.domain.resource.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

@HiltWorker
class UploadWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val storageRepository: StorageRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val imageBytes = inputData.getByteArray(KEY_IMAGE_DATA) ?: return Result.failure()
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            when (val result = storageRepository.uploadImage(bitmap)) {
                is Resource.Success -> Result.success()
                is Resource.Error -> if (shouldRetry(result)) Result.retry() else Result.failure()
                else -> Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun shouldRetry(error: Resource.Error): Boolean {
        return error.errorMessage.contains("network", ignoreCase = true)
    }

    companion object {
        const val KEY_IMAGE_DATA = "image_data"

        fun createWorkRequest(bitmap: Bitmap): OneTimeWorkRequest {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
                return OneTimeWorkRequestBuilder<UploadWorker>()
                    .setInputData(workDataOf(KEY_IMAGE_DATA to stream.toByteArray()))
                    .setBackoffCriteria(BackoffPolicy.LINEAR, 10_000L, TimeUnit.MILLISECONDS)
                    .build()
            }
        }
    }
}
