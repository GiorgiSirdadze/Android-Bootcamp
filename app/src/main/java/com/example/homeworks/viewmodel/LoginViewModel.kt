package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.repository.UserRepository
import com.example.homeworks.api.LoginRequestDto
import com.example.homeworks.api.LoginResponseDto
import com.example.homeworks.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<LoginResponseDto>?>(null)
    val loginState: StateFlow<Resource<LoginResponseDto>?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(LoginRequestDto(email, password))
            _loginState.value = result
            when (result) {
                is Resource.Success -> {
                    println("Login Success: ${result.data.token}")
                }
                is Resource.Error -> {
                    println("Login Error: ${result.errorMessage}")
                }
            }
        }
    }
}


