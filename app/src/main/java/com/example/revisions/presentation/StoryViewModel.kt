package com.example.revisions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisions.data.StoryItemDto
import com.example.revisions.repository.ItemRepository
import com.example.revisions.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _storyItem = MutableStateFlow<Resource<List<StoryItemDto>>>(Resource.Success(emptyList()))
    val storyItem: StateFlow<Resource<List<StoryItemDto>>> = _storyItem

    fun fetchStoryItems() {
        viewModelScope.launch {
            _storyItem.value = repository.fetchStories()
        }
    }
}
