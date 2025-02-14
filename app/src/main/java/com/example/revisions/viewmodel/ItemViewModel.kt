package com.example.revisions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisions.data.ItemDto
import com.example.revisions.repository.ItemRepository
import com.example.revisions.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _items = MutableStateFlow<Resource<List<ItemDto>>>(Resource.Success(emptyList()))
    val items: StateFlow<Resource<List<ItemDto>>> = _items

    fun fetchItems() {
        viewModelScope.launch {
            _items.value = repository.fetchItems()
        }
    }
}
