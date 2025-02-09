package com.example.homeworks.resource

import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall.invoke()

        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error("HTTP Error: ${response.code()} - ${response.message()}")
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Resource.Error("Network error: ${throwable.message}")
            is HttpException -> Resource.Error("HTTP error: ${throwable.message}")
            is IllegalStateException -> Resource.Error("Unexpected error: ${throwable.message}")
            else -> Resource.Error("Unknown error: ${throwable.message}")
        }
    }
}
