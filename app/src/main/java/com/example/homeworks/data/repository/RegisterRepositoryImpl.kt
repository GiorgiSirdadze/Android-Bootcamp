package com.example.homeworks.data.repository

import com.example.homeworks.data.mapper.toDomain
import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.data.remote.LoginRequestDto
import com.example.homeworks.data.remote.RegisterRequestDto
import com.example.homeworks.domain.model.RegisterResponse
import com.example.homeworks.domain.repository.RegisterRepository
import com.example.homeworks.data.resource.ApiHelper
import com.example.homeworks.data.resource.Resource
import com.example.homeworks.data.resource.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService,
    ) : RegisterRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<RegisterResponse>> =
        apiHelper.handleHttpRequest {
            apiService.register(RegisterRequestDto(email = email, password = password))
        }.mapResource {
            it.toDomain()
        }
    }

