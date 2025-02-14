package com.example.revisions.repository

import com.example.revisions.data.ApiService
import com.example.revisions.data.ItemDto
import com.example.revisions.resource.ApiHelper
import com.example.revisions.resource.Resource
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) {

    suspend fun fetchItems(): Resource<List<ItemDto>> {
        return apiHelper.handleHttpRequest {
            apiService.getItems()
        }
    }
}
