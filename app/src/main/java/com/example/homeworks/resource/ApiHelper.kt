package com.example.homeworks.resource

import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
object ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(errorMessage = "Unexpected empty response")
            } else {
                Resource.Error(errorMessage = response.message().takeIf { it.isNotBlank() } ?: "Unknown server error")
            }
        } catch (throwable: Throwable) {
            val errorMessage = when (throwable) {
                is IOException -> "Network error: Check your internet connection."
                is HttpException -> "HTTP error: ${throwable.response()?.errorBody()?.string() ?: "Unknown error"}"
                is IllegalStateException -> "Illegal state error: ${throwable.message ?: "Unknown issue"}"
                else -> "Unexpected error occurred: ${throwable.message ?: "Unknown error"}"
            }
            Resource.Error(errorMessage = errorMessage)
        }
    }
}
