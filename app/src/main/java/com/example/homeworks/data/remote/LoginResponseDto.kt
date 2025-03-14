package com.example.homeworks.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)

