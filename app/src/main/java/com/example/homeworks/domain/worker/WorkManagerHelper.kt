package com.example.homeworks.domain.worker

import android.graphics.Bitmap

interface WorkerManagerHelper {
    fun enqueueImageUpload(bitmap: Bitmap)
}