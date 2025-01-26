package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.api.ApiService
import com.example.homeworks.api.RetrofitClient
import com.example.homeworks.api.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.apiService

    // StateFlow to hold the users list
    private val _usersState = MutableStateFlow<List<User>?>(null)
    val usersState: StateFlow<List<User>?> get() = _usersState

    // StateFlow to handle errors
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    /**
     * Fetch users from the API and update the state flows.
     */
    fun fetchUsers(page: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers(page)
                _usersState.value = response.data
                _errorState.value = null
            } catch (e: Exception) {
                _usersState.value = null
                _errorState.value = e.message
            }
        }
    }
}
