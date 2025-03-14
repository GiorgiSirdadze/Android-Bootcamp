package com.example.homeworks.domain.usecase

import javax.inject.Inject

interface ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean
}

class ValidateEmailUseCaseImpl @Inject constructor() : ValidateEmailUseCase {
    override fun invoke(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
}
