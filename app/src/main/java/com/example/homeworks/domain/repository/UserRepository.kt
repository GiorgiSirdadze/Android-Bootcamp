package com.example.homeworks.domain.repository

import com.example.homeworks.domain.model.User
import com.example.homeworks.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUsers(page: Int): Flow<Resource<User>>
}
