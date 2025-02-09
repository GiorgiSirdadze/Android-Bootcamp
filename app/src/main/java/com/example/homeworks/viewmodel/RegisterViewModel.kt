package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.api.RegisterRequestDto
import com.example.homeworks.api.RetrofitClient
import com.example.homeworks.resource.Resource
import com.example.homeworks.resource.handleHttpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<String>?>(null)
    val registerState: StateFlow<Resource<String>?> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = handleHttpRequest {
                RetrofitClient.apiService.register(RegisterRequestDto(email, password))
            }

            _registerState.value = when (result) {
                is Resource.Success -> Resource.Success("Registration successful")
                is Resource.Error -> Resource.Error(result.errorMessage)
            }
        }
    }
}
