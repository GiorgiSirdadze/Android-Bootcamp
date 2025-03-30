package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.model.Account
import com.example.homeworks.domain.repository.FromAccountRepository
import com.example.homeworks.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAccountsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Account>>>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val repository: FromAccountRepository
) : GetAccountsUseCase {
    override suspend operator fun invoke(): Flow<Resource<List<Account>>> {
        return repository.getAccounts()
    }
}