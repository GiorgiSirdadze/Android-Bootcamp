package com.example.exploreit.data.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("9b00912e-5583-4471-8bac-4dd7d052b583")
    suspend fun getTours(): Response<List<TourDto>>
}