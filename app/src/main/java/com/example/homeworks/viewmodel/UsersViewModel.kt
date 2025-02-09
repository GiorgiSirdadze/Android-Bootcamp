package com.example.homeworks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworks.paging.UserPagingSource
import com.example.homeworks.api.RetrofitClient
import com.example.homeworks.api.User
import kotlinx.coroutines.flow.Flow

class UsersViewModel : ViewModel() {

    private val apiService = RetrofitClient.apiService

    val userPagingFlow: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            prefetchDistance = 1,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UserPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}