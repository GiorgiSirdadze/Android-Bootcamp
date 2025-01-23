package com.example.homeworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String>?> = _loginResult

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    val token = response.body()?.token.orEmpty()
                    _loginResult.value = Result.success(token)
                    _token.value = token
                } else {
                    _loginResult.value = Result.failure(Exception("Login failed: ${response.errorBody()?.string()}"))
                }
            } catch (e: HttpException) {
                _loginResult.value = Result.failure(Exception("HttpException: ${e.message}"))
            } catch (e: IOException) {
                _loginResult.value = Result.failure(Exception("IOException: ${e.message}"))
            }
        }
    }
}