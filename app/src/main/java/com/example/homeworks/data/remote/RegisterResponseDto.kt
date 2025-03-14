package com.example.homeworks.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val token: String
)

