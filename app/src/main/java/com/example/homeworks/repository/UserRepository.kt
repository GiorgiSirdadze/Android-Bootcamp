package com.example.homeworks.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworks.api.ApiService
import com.example.homeworks.api.LoginRequestDto
import com.example.homeworks.api.LoginResponseDto
import com.example.homeworks.api.RegisterRequestDto
import com.example.homeworks.api.RegisterResponseDto
import com.example.homeworks.api.UserDto
import com.example.homeworks.local.UserDao
import com.example.homeworks.local.UserEntity
import com.example.homeworks.resource.ApiHelper
import com.example.homeworks.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: ApiService,
    private val userDao : UserDao
) {
    suspend fun fetchUsersFromApi(): Resource<UserDto> {
        return apiHelper.handleHttpRequest {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { userDto ->
                    val userEntities = userDto.data.map { user ->
                        UserEntity(
                            id = user.id,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email,
                            avatar = user.avatar
                        )
                    }
                    userDao.insertUsers(userEntities)
                }
            }
            response
        }
    }

    suspend fun login(request: LoginRequestDto): Resource<LoginResponseDto> {
        return apiHelper.handleHttpRequest {
            apiService.login(request)
        }
    }
    suspend fun register(request: RegisterRequestDto): Resource<RegisterResponseDto> {
        return apiHelper.handleHttpRequest {
            apiService.register(request)
        }
    }
    fun getPagedUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { userDao.getPagedUsers() }
        ).flow
    }
}



