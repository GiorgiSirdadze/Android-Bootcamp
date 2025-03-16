package com.example.homeworks.presentation.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.usecase.FetchCategoriesUseCase
import com.example.homeworks.presentation.mapper.toPresentation
import com.example.homeworks.utils.flattenCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> get() = _state

    private val _uiEvents = Channel<CategoryUiEvents>()
    val uiEvents get() = _uiEvents.receiveAsFlow()

    private val debouncePeriod = 300L

    fun onEvent(event : CategoryEvent){
        when(event){
            is CategoryEvent.SearchQuery -> searchQuery(query = event.query)
        }
    }

    private fun fetchCategories(query: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(loader = true, error = null) }

            fetchCategoriesUseCase(query = query).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val flattenedCategories = resource.data.flattenCategories()
                        _state.update {
                            it.copy(
                                categories = flattenedCategories.toPresentation(),
                                loader = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                loader = false,
                                error = resource.errorMessage
                            )
                        }
                        _uiEvents.send(CategoryUiEvents.ShowError(resource.errorMessage))
                    }
                    is Resource.Loader -> {
                        _state.update { it.copy(loader = resource.isLoading) }
                    }
                }
            }
        }
    }

  private  fun searchQuery(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(loader = true, error = null) }
            Log.d("Search Query", "Query: $query")
            delay(debouncePeriod)
            fetchCategories(query)
        }
    }
}