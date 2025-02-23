package com.example.revisions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisions.data.PostItem
import com.example.revisions.repository.ItemRepository
import com.example.revisions.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {


    private val _postItems = MutableStateFlow<Resource<List<PostItem>>>(Resource.Success(emptyList()))
    val postItems: StateFlow<Resource<List<PostItem>>> = _postItems

    fun fetchPostItems() {
        viewModelScope.launch {
            _postItems.value = repository.fetchPosts()
        }
    }
}
