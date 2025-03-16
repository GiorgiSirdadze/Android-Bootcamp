package com.example.homeworks.domain.repository

import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun fetchCategories(): Flow<Resource<List<Category>>>
}