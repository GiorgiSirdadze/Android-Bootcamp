package com.example.revisions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.revisions.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val users = repository.allUsers

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun refreshUsers() {
        viewModelScope.launch {
            repository.fetchUsers().collect { result ->
                result.onSuccess { _isLoading.value = false }
                result.onFailure { _isLoading.value = false }
            }
        }
    }

    companion object {
        fun provideFactory(repository: UserRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                        return UserViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}


