package com.example.homeworks.presentation.screen.from

sealed interface FromUiEvents {
    data class ShowError(val message: String) : FromUiEvents
    data object DismissBottomSheet : FromUiEvents
}