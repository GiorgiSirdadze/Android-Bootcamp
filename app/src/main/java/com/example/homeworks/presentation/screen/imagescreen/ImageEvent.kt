package com.example.homeworks.presentation.screen.imagescreen

import android.graphics.Bitmap
import android.net.Uri

sealed class ImageEvent {
    data object ShowImagePicker : ImageEvent()
    data class ImageSourceSelected(val isCamera: Boolean) : ImageEvent()
    data class CameraResult(val bitmap: Bitmap?) : ImageEvent()
    data class GalleryResult(val uri: Uri?) : ImageEvent()
    data object UploadImage : ImageEvent()
}