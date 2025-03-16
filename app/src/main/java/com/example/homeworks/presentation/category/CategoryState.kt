package com.example.homeworks.presentation.category

import com.example.homeworks.presentation.model.Categories

data class CategoryState (
    val loader : Boolean = false,
    val error : String? = null,
    val categories: List<Categories> = emptyList()
)
