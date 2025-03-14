package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.domain.repository.LoginRepository
import com.example.homeworks.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginResponse>>
}

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginResponse>> =
        loginRepository.login(email = email, password = password)

}
