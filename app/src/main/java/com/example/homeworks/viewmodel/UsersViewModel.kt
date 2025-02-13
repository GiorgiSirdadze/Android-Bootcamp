package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworks.local.UserEntity
import com.example.homeworks.repository.UserRepository
import com.example.homeworks.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userPagingFlow: Flow<PagingData<UserEntity>> =
        userRepository.getPagedUsers().cachedIn(viewModelScope)

    // Fetch from API and store in Room before displaying paginated data
    fun syncUsersFromApi() {
        viewModelScope.launch {
            val result = userRepository.fetchUsersFromApi()
            when (result) {
                is Resource.Success -> {
                    println("Users synced from API and saved in Room")
                }
                is Resource.Error -> {
                    println("Error syncing users: ${result.errorMessage}")
                }
            }
        }
    }
}

