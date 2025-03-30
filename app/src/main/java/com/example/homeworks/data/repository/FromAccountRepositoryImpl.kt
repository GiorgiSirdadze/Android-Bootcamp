package com.example.homeworks.data.repository

import com.example.homeworks.data.mapper.toDomain
import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.domain.model.Account
import com.example.homeworks.domain.repository.FromAccountRepository
import com.example.homeworks.domain.resource.ApiHelper
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.resource.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FromAccountRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) : FromAccountRepository {
    override suspend fun getAccounts(): Flow<Resource<List<Account>>> =
        apiHelper.handleHttpRequest {
            apiService.getAccounts()
        }.mapResource { response ->
            response.map { it.toDomain() }
        }
}