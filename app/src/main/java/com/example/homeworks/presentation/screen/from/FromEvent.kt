package com.example.homeworks.presentation.screen.from

import com.example.homeworks.presentation.model.Card

sealed class FromEvent {
    data object GetAccounts : FromEvent()
    data class SelectAccount(val card: Card) : FromEvent()
}