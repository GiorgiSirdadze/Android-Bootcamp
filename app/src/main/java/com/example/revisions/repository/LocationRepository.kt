package com.example.revisions.repository

import com.example.revisions.data.ApiService
import com.example.revisions.data.LocationDto
import com.example.revisions.resource.ApiHelper
import com.example.revisions.resource.Resource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) {
    suspend fun getLocations(): Resource<List<LocationDto>> {
        return apiHelper.handleHttpRequest { apiService.getLocations() }
    }
}
