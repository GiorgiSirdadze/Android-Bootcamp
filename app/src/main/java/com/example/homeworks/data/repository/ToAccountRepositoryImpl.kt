package com.example.homeworks.data.repository

import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.data.helper.verifyViaApi
import com.example.homeworks.domain.model.AccountVerificationResult
import com.example.homeworks.domain.repository.ToAccountRepository
import javax.inject.Inject

class ToAccountRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ToAccountRepository {

    override suspend fun verifyAccount(accountNumber: String): AccountVerificationResult {
        if (accountNumber.length != 23) {
            return AccountVerificationResult(
                isValid = false,
                errorMessage = "Account must be 23 characters"
            )
        }
        return verifyViaApi { apiService.verifyAccount(accountNumber) }
    }

    override suspend fun verifyPersonalNumber(personalNumber: String): AccountVerificationResult {
        if (personalNumber.length != 11 || !personalNumber.all { it.isDigit() }) {
            return AccountVerificationResult(
                isValid = false,
                errorMessage = "Personal number must be 11 digits"
            )
        }
        return verifyViaApi { apiService.verifyAccount(personalNumber) }
    }

    override suspend fun verifyPhoneNumber(phoneNumber: String): AccountVerificationResult {
        if (phoneNumber.length != 9 || !phoneNumber.all { it.isDigit() }) {
            return AccountVerificationResult(
                isValid = false,
                errorMessage = "Phone number must be 9 digits"
            )
        }
        return verifyViaApi { apiService.verifyAccount(phoneNumber) }
    }
}