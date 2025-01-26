package com.example.homeworks.api

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)

