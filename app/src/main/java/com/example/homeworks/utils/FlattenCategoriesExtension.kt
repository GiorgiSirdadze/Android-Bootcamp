package com.example.homeworks.utils

import com.example.homeworks.domain.model.Category

fun List<Category>.flattenCategories(): List<Category> {
    val flattenedList = mutableListOf<Category>()
    for (category in this) {
        flattenedList.add(category)
        if (category.children.isNotEmpty()) {
            flattenedList.addAll(category.children.flattenCategories())
        }
    }
    return flattenedList
}