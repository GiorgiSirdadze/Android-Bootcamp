package com.example.homeworks

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequestDto): Response<LoginResponseDto>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequestDto): Response<RegisterResponseDto>
}

