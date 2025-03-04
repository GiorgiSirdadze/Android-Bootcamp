package com.example.exploreit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.exploreit.data.mapper.Tour
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _favoriteTours = MutableStateFlow<MutableList<Tour>>(mutableListOf())
    val favoriteTours: StateFlow<MutableList<Tour>> get() = _favoriteTours

    fun addTourToFavorites(tour: Tour) {
        val currentFavorites = _favoriteTours.value
        if (!currentFavorites.contains(tour)) {
            currentFavorites.add(tour)
            _favoriteTours.value = currentFavorites
        }
    }
}
