package com.example.homeworks.presentation.screen.from

import com.example.homeworks.presentation.model.Card

data class FromState (
    val cards: List<Card>? = null,
    val selectedCard: Card? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
