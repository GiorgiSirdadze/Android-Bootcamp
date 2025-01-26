package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.api.RegisterRequestDto
import com.example.homeworks.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableStateFlow<Result<String>?>(null)
    val registerResult: StateFlow<Result<String>?> = _registerResult

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(RegisterRequestDto(email = email, password = password))
                if (response.isSuccessful) {
                    val token = response.body()?.token.orEmpty()
                    _registerResult.value = Result.success(token)
                    _token.value = token
                } else {
                    _registerResult.value = Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
                }
            } catch (e: HttpException) {
                _registerResult.value = Result.failure(Exception("HttpException: ${e.message}"))
            } catch (e: IOException) {
                _registerResult.value = Result.failure(Exception("IOException: ${e.message}"))
            }
        }
    }
}