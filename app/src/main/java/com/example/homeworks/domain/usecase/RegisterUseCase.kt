package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.model.RegisterResponse
import com.example.homeworks.domain.repository.RegisterRepository
import com.example.homeworks.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RegisterUseCase {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterResponse>>
}

class RegisterUseCaseImpl @Inject constructor(
    private val registerRepository: RegisterRepository
) : RegisterUseCase {
    override suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterResponse>> =
        registerRepository.register(email = email, password = password)

}
