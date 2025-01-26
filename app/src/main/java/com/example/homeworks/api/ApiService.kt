package com.example.homeworks.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequestDto): Response<LoginResponseDto>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequestDto): Response<RegisterResponseDto>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): UserResponse
}

