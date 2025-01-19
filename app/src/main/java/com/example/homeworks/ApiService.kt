package com.example.homeworks

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): Response<ApiResponseDto>

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): Response<ApiResponseDto>
}
