package com.example.exploreit.data.repository

import com.example.exploreit.data.api.ApiService
import com.example.exploreit.data.mapper.Tour
import com.example.exploreit.data.mapper.toPresenter
import com.example.exploreit.resource.ApiHelper
import com.example.exploreit.resource.Resource
import javax.inject.Inject

class TourRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) {

    suspend fun fetchTours(): Resource<List<Tour>> {
        return try {
            Resource.Loading
            val response = apiHelper.handleHttpRequest {
                apiService.getTours()
            }
            when (response) {
                is Resource.Success -> {
                    val tour = response.data.map { it.toPresenter() }
                    Resource.Success(tour)
                }
                is Resource.Error -> {
                    response
                }
                else -> Resource.Error("Unknown Error")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}
