package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.repository.UserRepository
import com.example.homeworks.api.RegisterRequestDto
import com.example.homeworks.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<String>?>(null)
    val registerState: StateFlow<Resource<String>?> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.register(RegisterRequestDto(email, password))

            _registerState.value = when (result) {
                is Resource.Success -> {
                    println("Registration Success")
                    Resource.Success("Registration successful")
                }
                is Resource.Error -> {
                    println("Registration Error: ${result.errorMessage}")
                    Resource.Error(result.errorMessage)
                }
            }
        }
    }
}
