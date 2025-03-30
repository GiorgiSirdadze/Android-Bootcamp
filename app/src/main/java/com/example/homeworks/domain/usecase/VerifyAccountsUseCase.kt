package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.model.AccountType
import com.example.homeworks.domain.model.AccountVerificationResult
import com.example.homeworks.domain.repository.ToAccountRepository
import javax.inject.Inject

interface VerifyAccountsUseCase {
    suspend operator fun invoke(
        type: AccountType,
        input: String
    ): AccountVerificationResult
}

class VerifyAccountsUseCaseImpl @Inject constructor(
    private val repository: ToAccountRepository
) : VerifyAccountsUseCase {
    override suspend fun invoke(
        type: AccountType,
        input: String
    ): AccountVerificationResult {
        return when (type) {
            AccountType.ACCOUNT -> repository.verifyAccount(input)
            AccountType.PERSONAL -> repository.verifyPersonalNumber(input)
            AccountType.PHONE -> repository.verifyPhoneNumber(input)
        }
    }
}