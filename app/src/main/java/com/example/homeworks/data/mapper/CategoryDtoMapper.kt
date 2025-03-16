package com.example.homeworks.data.mapper

import com.example.homeworks.data.remote.CategoryDto
import com.example.homeworks.domain.model.Category

fun CategoryDto.toDomain(parentDepth: Int = 0): Category {
    return Category(
        id = this.id,
        name = this.name,
        nameDe = this.nameDe,
        createdAt = this.createdAt,
        bglNumber = this.bglNumber,
        bglVariant = this.bglVariant,
        orderId = this.orderId,
        main = this.main,
        children = this.children.map { it.toDomain(parentDepth + 1) },
        depth = parentDepth
    )
}