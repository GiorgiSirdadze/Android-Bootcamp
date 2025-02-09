package com.example.homeworks.api

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val token: String
)

