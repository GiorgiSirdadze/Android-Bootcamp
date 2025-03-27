package com.example.homeworks.domain.usecase

import android.graphics.Bitmap
import com.example.homeworks.domain.worker.WorkerManagerHelper
import javax.inject.Inject

interface UploadImageUseCase {
    operator fun invoke(bitmap: Bitmap)
}
class UploadImageUseCaseImpl @Inject constructor(
    private val workerManagerHelper: WorkerManagerHelper
) : UploadImageUseCase {
    override operator fun invoke(bitmap: Bitmap) {
        workerManagerHelper.enqueueImageUpload(bitmap)
    }
}