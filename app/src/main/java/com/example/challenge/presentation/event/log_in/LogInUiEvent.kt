package com.example.challenge.presentation.event.log_in

sealed interface LogInUiEvent {
    data object NavigateToConnections : LogInUiEvent
}