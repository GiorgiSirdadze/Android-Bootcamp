package com.example.homeworks.data.remote


import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun getCategories(): Response<List<CategoryDto>>

}

