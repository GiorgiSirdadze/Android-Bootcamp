package com.example.homeworks

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)

