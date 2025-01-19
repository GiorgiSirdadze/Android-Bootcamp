package com.example.homeworks

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto(
    val token: String?,
    val error: String?
)

