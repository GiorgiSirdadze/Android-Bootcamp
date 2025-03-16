package com.example.homeworks.presentation.model


data class Categories (
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String? = null,
    val bglVariant: String? = null,
    val orderId: Int? = null,
    val main: String? = null,
    val children: List<Categories> = emptyList(),
    val depth: Int = 0
)
