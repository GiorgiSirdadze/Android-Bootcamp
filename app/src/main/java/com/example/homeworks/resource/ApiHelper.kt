package com.example.homeworks.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            emit(Resource.Loader(isLoading = true))
            val response = apiCall.invoke()
            try {
                if (response.isSuccessful) {
                    emit( response.body()?.let {
                        Resource.Success(data = it)
                    } ?: Resource.Error(errorMessage = "Unexpected empty response"))

                } else {
                    emit(Resource.Error(errorMessage = response.message()))
                }
                emit(Resource.Loader(isLoading = false))
            } catch (throwable: Throwable) {
                val errorMessage = when (throwable) {
                    is IOException -> "Network error: Check your internet connection."
                    is HttpException -> "HTTP error: ${
                        throwable.response()?.errorBody()?.string() ?: "Unknown error"
                    }"
                    is IllegalStateException -> "Illegal state error: ${throwable.message ?: "Unknown issue"}"
                    else -> "Unexpected error occurred: ${throwable.message ?: "Unknown error"}"
                }
                emit(Resource.Error(errorMessage = errorMessage))
                emit(Resource.Loader(isLoading = false))
            }
        }
}
