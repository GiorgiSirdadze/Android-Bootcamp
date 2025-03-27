package com.example.homeworks.presentation.screen.imagescreen

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.usecase.CompressBitmapUseCase
import com.example.homeworks.domain.usecase.CompressImageFromUriUseCase
import com.example.homeworks.domain.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val compressBitmapUseCase: CompressBitmapUseCase,
    private val compressImageFromUriUseCase: CompressImageFromUriUseCase,
    private val uploadImageUseCase : UploadImageUseCase
) : ViewModel() {


    private val _uiEvents = Channel<ImageUiEvents>()
    val uiEvents get() = _uiEvents.receiveAsFlow()

    private val _state = MutableStateFlow(ImageState())
    val state : StateFlow<ImageState> get() = _state


    fun onEvent(event: ImageEvent) {
        when (event) {
            is ImageEvent.ShowImagePicker -> showImagePicker()
            is ImageEvent.ImageSourceSelected -> onImageSourceSelected(event.isCamera)
            is ImageEvent.CameraResult -> handleCameraResult(event.bitmap)
            is ImageEvent.GalleryResult -> handleGalleryResult(event.uri)
            is ImageEvent.UploadImage -> uploadImage()
        }
    }


    private fun uploadImage() {
        val currentBitmap = _state.value.imageBitmap ?: run {
            _uiEvents.trySend(ImageUiEvents.ShowError("No image selected"))
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            uploadImageUseCase(currentBitmap)
            _uiEvents.trySend(ImageUiEvents.ShowNotification(
                "Upload Started",
                "Image will be uploaded in background"
            ))
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun showImagePicker() {
        viewModelScope.launch {
            _uiEvents.send(ImageUiEvents.ShowBottomSheet)
        }
    }

   private  fun onImageSourceSelected(isCamera: Boolean) {
        viewModelScope.launch {
            _uiEvents.send(if (isCamera) ImageUiEvents.OpenCamera else ImageUiEvents.OpenGallery)
        }
    }

   private fun handleCameraResult(bitmap: Bitmap?) {
        bitmap?.let { rawBitmap ->
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                when (val result = compressBitmapUseCase(rawBitmap)) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                imageBitmap = result.data,
                                isLoading = false
                            )
                        }
                        _uiEvents.send(
                            ImageUiEvents.ShowNotification(
                                "Image Set Successfully",
                                "Your image was set successfully!"
                            )
                        )
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvents.send(ImageUiEvents.ShowError(result.errorMessage))
                    }
                    is Resource.Loader -> Unit
                }
            }
        }
    }

    private fun handleGalleryResult(uri: Uri?) {
        uri?.let { imageUri ->
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                when (val result = compressImageFromUriUseCase(imageUri)) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                imageBitmap = result.data,
                                isLoading = false
                            )
                        }
                        _uiEvents.send(
                            ImageUiEvents.ShowNotification(
                                "Image Set Successfully",
                                "Your image was set successfully!"
                            )
                        )
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvents.send(ImageUiEvents.ShowError(result.errorMessage))
                    }
                    is Resource.Loader -> Unit
                }
            }
        }
    }

}
