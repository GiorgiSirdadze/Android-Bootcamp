package com.example.homework12

data class Orders(
    val id: Int,
    val trackNumber: String,
    val orderDate: String,
    val quantity: String,
    val subtotal: String,
    var status: String
)
