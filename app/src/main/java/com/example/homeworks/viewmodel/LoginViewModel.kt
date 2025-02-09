package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.api.LoginRequestDto
import com.example.homeworks.api.RetrofitClient
import com.example.homeworks.resource.Resource
import com.example.homeworks.resource.handleHttpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<String>?>(null)
    val loginState: StateFlow<Resource<String>?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = handleHttpRequest {
                RetrofitClient.apiService.login(LoginRequestDto(email, password))
            }

            _loginState.value = when (result) {
                is Resource.Success -> Resource.Success(result.data.token)
                is Resource.Error -> Resource.Error(result.errorMessage)
            }
        }
    }


}
