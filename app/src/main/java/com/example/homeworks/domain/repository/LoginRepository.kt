package com.example.homeworks.domain.repository

import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email : String, password : String): Flow<Resource<LoginResponse>>
}