package com.example.revisions.repository

import com.example.revisions.data.ApiService
import com.example.revisions.data.PostItem
import com.example.revisions.data.StoryItemDto
import com.example.revisions.data.toPresenter
import com.example.revisions.resource.ApiHelper
import com.example.revisions.resource.Resource
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) {

    suspend fun fetchPosts(): Resource<List<PostItem>> {
        val response = apiHelper.handleHttpRequest {
            apiService.getPosts()
        }
        return when (response) {
            is Resource.Success -> {
                val postItems = response.data.map { it.toPresenter() }
                Resource.Success(postItems)
            }
            is Resource.Error -> {
                response
            }
        }
    }
    suspend fun fetchStories(): Resource<List<StoryItemDto>>{
        return apiHelper.handleHttpRequest {
            apiService.getStories()
        }
    }
}


