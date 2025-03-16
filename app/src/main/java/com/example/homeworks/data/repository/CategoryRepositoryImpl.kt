package com.example.homeworks.data.repository

import com.example.homeworks.data.mapper.toDomain
import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.domain.resource.ApiHelper
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.resource.mapResource
import com.example.homeworks.domain.model.Category
import com.example.homeworks.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService
) : CategoryRepository {
    override suspend fun fetchCategories(): Flow<Resource<List<Category>>> {
        return apiHelper.handleHttpRequest {
            apiService.getCategories()
        }.mapResource { categoryDtoList ->
            categoryDtoList.map { it.toDomain() }
        }
    }
}