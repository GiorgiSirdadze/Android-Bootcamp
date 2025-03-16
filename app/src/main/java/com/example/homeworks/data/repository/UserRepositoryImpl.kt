package com.example.homeworks.data.repository

import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.data.mapper.toDomain
import com.example.homeworks.domain.model.User
import com.example.homeworks.domain.repository.UserRepository
import com.example.homeworks.data.resource.ApiHelper
import com.example.homeworks.data.resource.Resource
import com.example.homeworks.data.resource.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService,

    ) : UserRepository {

   override suspend fun fetchUsers(page: Int): Flow<Resource<User>> =
        apiHelper.handleHttpRequest {
            apiService.getUsers(page)
        }.mapResource {
            it.toDomain()
        }



}


