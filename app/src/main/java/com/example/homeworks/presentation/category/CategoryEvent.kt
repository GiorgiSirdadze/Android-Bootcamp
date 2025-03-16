package com.example.homeworks.presentation.category

sealed class CategoryEvent {
    data class SearchQuery(val query : String) : CategoryEvent()
}