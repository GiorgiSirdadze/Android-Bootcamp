package com.example.homeworks.domain.repository

import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.domain.model.RegisterResponse
import com.example.homeworks.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(email : String, password : String): Flow<Resource<RegisterResponse>>
}