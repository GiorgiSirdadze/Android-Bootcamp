package com.example.homeworks.domain.repository

import com.example.homeworks.domain.model.AccountVerificationResult

interface ToAccountRepository {
    suspend fun verifyAccount(accountNumber: String): AccountVerificationResult

    suspend fun verifyPersonalNumber(personalNumber: String): AccountVerificationResult

    suspend fun verifyPhoneNumber(phoneNumber: String): AccountVerificationResult
}