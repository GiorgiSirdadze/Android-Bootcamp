//package com.example.homeworks.presentation.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.paging.PagingData
//import androidx.paging.cachedIn
//import com.example.homeworks.data.local.UserEntity
//import com.example.homeworks.data.repository.UserRepositoryImpl
//import com.example.homeworks.resource.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class UsersViewModel @Inject constructor(
//    private val userRepositoryImpl: UserRepositoryImpl
//) : ViewModel() {
//
//
//    fun syncUsersFromApi() {
//        viewModelScope.launch {
//            val result = userRepositoryImpl.fetchUsers()
//            when (result) {
//                is Resource.Success -> {
//                    println("Users synced from API and saved in Room")
//                }
//                is Resource.Error -> {
//                    println("Error syncing users: ${result.errorMessage}")
//                }
//            }
//        }
//    }
//}
//
