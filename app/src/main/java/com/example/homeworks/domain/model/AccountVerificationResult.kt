package com.example.homeworks.domain.model

data class AccountVerificationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)