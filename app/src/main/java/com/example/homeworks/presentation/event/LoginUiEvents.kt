package com.example.homeworks.presentation.event

sealed interface LoginUiEvents {

    data class ActivateLoginButton(
        val isEnabled: Boolean
    ) : LoginUiEvents

    data object ShowEmailError : LoginUiEvents
}
