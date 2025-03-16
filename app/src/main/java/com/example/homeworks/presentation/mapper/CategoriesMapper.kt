package com.example.homeworks.presentation.mapper

import com.example.homeworks.domain.model.Category
import com.example.homeworks.presentation.model.Categories

fun Category.toPresentation(): Categories {
    return Categories(
        id = this.id,
        name = this.name,
        nameDe = this.nameDe,
        createdAt = this.createdAt,
        bglNumber = this.bglNumber,
        bglVariant = this.bglVariant,
        orderId = this.orderId,
        main = this.main,
        children = this.children.toPresentation(),
        depth = this.depth
    )
}

fun List<Category>.toPresentation(): List<Categories> {
    return this.map { it.toPresentation() }
}