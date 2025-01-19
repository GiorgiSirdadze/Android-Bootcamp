package com.example.homeworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Result<ApiResponseDto>>()
    val loginResult: LiveData<Result<ApiResponseDto>> get() = _loginResult

    private val _registerResult = MutableLiveData<Result<ApiResponseDto>>()
    val registerResult: LiveData<Result<ApiResponseDto>> get() = _registerResult

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginResult.value = Result.failure(Throwable("Fields cannot be empty"))
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequestDto(email, password))
                handleResponse(response, _loginResult)
            } catch (e: HttpException) {
                _loginResult.value = Result.failure(Throwable("Server error: ${e.message()}"))
            } catch (e: Exception) {
                _loginResult.value = Result.failure(Throwable("Unexpected error: ${e.message}"))
            }
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _registerResult.value = Result.failure(Throwable("Fields cannot be empty"))
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(RegisterRequestDto(email, password))
                handleResponse(response, _registerResult)
            } catch (e: HttpException) {
                _registerResult.value = Result.failure(Throwable("Server error: ${e.message()}"))
            } catch (e: Exception) {
                _registerResult.value = Result.failure(Throwable("Unexpected error: ${e.message}"))
            }
        }
    }

    private fun handleResponse(
        response: Response<ApiResponseDto>,
        resultLiveData: MutableLiveData<Result<ApiResponseDto>>
    ) {
        if (response.isSuccessful && response.body() != null) {
            resultLiveData.value = Result.success(response.body()!!)
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            resultLiveData.value = Result.failure(Throwable(errorBody))
        }
    }
}
