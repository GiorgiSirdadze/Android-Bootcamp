package com.example.homeworks.domain.model

data class Account(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valuteType: String,
    val cardType: String,
    val balance: Double,
    val cardLogo: String?
)