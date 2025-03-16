package com.example.homeworks.presentation.event

sealed interface LoginUiEvents {
    data class ActivateLoginButton(val isEnabled: Boolean) : LoginUiEvents
    data class ShowError(val errorMessage: String) : LoginUiEvents
    data object NavigateToHomeScreen : LoginUiEvents
}