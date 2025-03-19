package com.example.challenge.presentation.event.splash

sealed interface SplashUiEvent {
    data object NavigateToConnections : SplashUiEvent
    data object NavigateToLogIn: SplashUiEvent
}