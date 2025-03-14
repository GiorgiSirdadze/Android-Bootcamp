package com.example.homeworks.data.repository

import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.data.remote.LoginRequestDto
import com.example.homeworks.data.mapper.toDomain
import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.domain.repository.LoginRepository
import com.example.homeworks.resource.ApiHelper
import com.example.homeworks.resource.Resource
import com.example.homeworks.resource.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl@Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService,

    ) : LoginRepository {
    override suspend fun login(email: String, password : String): Flow<Resource<LoginResponse>> =
        apiHelper.handleHttpRequest {
            apiService.login(LoginRequestDto(email = email, password = password))
        }.mapResource {
            it.toDomain()
        }
}
