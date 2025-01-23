package com.example.homeworks

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val token: String
)

