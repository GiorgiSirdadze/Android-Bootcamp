package com.example.homeworks.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object RetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"
    private val contentType = "application/json".toMediaType()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

