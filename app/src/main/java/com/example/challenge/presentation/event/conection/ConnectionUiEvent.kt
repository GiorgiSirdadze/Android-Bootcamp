package com.example.challenge.presentation.event.conection

sealed interface ConnectionUiEvent {
    data object NavigateToLogIn : ConnectionUiEvent
}