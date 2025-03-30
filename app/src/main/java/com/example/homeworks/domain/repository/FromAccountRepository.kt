package com.example.homeworks.domain.repository

import com.example.homeworks.domain.model.Account
import com.example.homeworks.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface FromAccountRepository {
    suspend fun getAccounts() : Flow<Resource<List<Account>>>
}