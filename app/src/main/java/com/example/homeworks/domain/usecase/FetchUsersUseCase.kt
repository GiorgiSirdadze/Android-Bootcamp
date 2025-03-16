package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.model.User
import com.example.homeworks.domain.repository.UserRepository
import com.example.homeworks.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUsersUseCase{
    suspend  operator  fun <T> invoke(page: Int): Flow<Resource<User>>
}

class FetchUsersUseCaseImpl @Inject constructor(
    private val userRepository : UserRepository
) : FetchUsersUseCase {
    override suspend fun <T> invoke(page: Int): Flow<Resource<User>> =
        userRepository.fetchUsers(page = page)
}