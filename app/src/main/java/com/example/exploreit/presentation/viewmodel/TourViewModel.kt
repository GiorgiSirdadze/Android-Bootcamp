package com.example.exploreit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploreit.data.mapper.Tour
import com.example.exploreit.data.repository.TourRepository
import com.example.exploreit.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val repository: TourRepository
) : ViewModel() {

    private val _tourItems = MutableStateFlow<Resource<List<Tour>>>(Resource.Loading)
    val tourItems: StateFlow<Resource<List<Tour>>> = _tourItems

    fun fetchTourItems() {
        viewModelScope.launch {
            _tourItems.value = Resource.Loading
            val result = repository.fetchTours()
            _tourItems.value = result
        }
    }
}
