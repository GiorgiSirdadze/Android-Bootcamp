package com.example.homeworks.presentation.event

interface RegisterUiEvents {
    data class ActivateRegisterButton(val isEnabled: Boolean) : RegisterUiEvents
    data class ShowError(val errorMessage: String) : RegisterUiEvents
    data object RegistrationSuccessful : RegisterUiEvents
}