package com.example.homeworks.presentation.screen.imagescreen

sealed interface ImageUiEvents {
    data class ShowNotification(val title: String, val message: String) : ImageUiEvents
    data object OpenCamera : ImageUiEvents
    data object OpenGallery : ImageUiEvents
    data object ShowBottomSheet : ImageUiEvents
    data class ShowError(val error: String) : ImageUiEvents
}