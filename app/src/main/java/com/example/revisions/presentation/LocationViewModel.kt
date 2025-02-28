package com.example.revisions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revisions.data.LocationDto
import com.example.revisions.repository.LocationRepository
import com.example.revisions.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locations = MutableStateFlow<Resource<List<LocationDto>>>(Resource.Success(
        emptyList()
    ))
    val locations: StateFlow<Resource<List<LocationDto>>> = _locations

    fun getLocations() {
        viewModelScope.launch {
            _locations.value = locationRepository.getLocations()
        }
    }
}
