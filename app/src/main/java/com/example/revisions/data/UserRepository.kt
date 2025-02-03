package com.example.revisions.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepository(private val userDao: UserDao, private val apiService: ApiService) {

    val allUsers: Flow<List<UserEntity>> = userDao.getAllUsers()

    fun fetchUsers(): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(Unit))
            val response = apiService.getUsers()
            if (response.status) {
                val userEntities = response.users.map {
                    UserEntity(it.id, it.avatar, it.firstName, it.lastName, it.about, it.activationStatus)
                }
                userDao.deleteAllUsers()
                userDao.insertUsers(userEntities)
            }
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { e -> emit(Result.failure(e)) }
}
