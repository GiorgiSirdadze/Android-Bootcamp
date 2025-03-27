package com.example.homeworks.data.worker

import android.graphics.Bitmap
import androidx.work.WorkManager
import com.example.homeworks.domain.worker.WorkerManagerHelper
import javax.inject.Inject

class WorkerManagerHelperImpl @Inject constructor(
    private val workManager: WorkManager
) : WorkerManagerHelper {
    override fun enqueueImageUpload(bitmap: Bitmap) {
        val uploadWork = UploadWorker.createWorkRequest(bitmap)
        workManager.enqueue(uploadWork)
    }
}