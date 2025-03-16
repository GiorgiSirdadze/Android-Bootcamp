package com.example.homeworks.presentation.category

sealed interface CategoryUiEvents {
    data class ShowError(val errorMessage: String) : CategoryUiEvents

}