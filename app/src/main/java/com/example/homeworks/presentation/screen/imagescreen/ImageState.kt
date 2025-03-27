package com.example.homeworks.presentation.screen.imagescreen

import android.graphics.Bitmap

data class ImageState (
    val imageBitmap: Bitmap? = null,
    val isLoading: Boolean = false
)