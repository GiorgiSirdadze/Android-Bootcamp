package com.example.homeworks.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homeworks.UserPreferences
import com.example.homeworks.datastore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _userData = MutableStateFlow(UserPreferences.getDefaultInstance())
    val userData: StateFlow<UserPreferences> = _userData

    init {
        viewModelScope.launch {
            dataStoreManager.userPreferences.collectLatest { preferences ->
                _userData.value = preferences
            }
        }
    }

    fun saveUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            dataStoreManager.saveUser(firstName, lastName, email)
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dataStoreManager = DataStoreManager(context)
                UserViewModel(dataStoreManager)
            }
        }
    }
}
