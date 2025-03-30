package com.example.homeworks.presentation.screen.from

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.usecase.GetAccountsUseCase
import com.example.homeworks.presentation.mapper.toPresentation
import com.example.homeworks.presentation.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FromViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FromState())
    val state: StateFlow<FromState> = _state

    private val _uiEvents = Channel<FromUiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()


     fun onEvent(event: FromEvent) {
        when (event) {
            is FromEvent.GetAccounts -> getAccounts()
            is FromEvent.SelectAccount -> selectAccount(event.card)
        }
    }

    private fun getAccounts() {
        viewModelScope.launch {
            getAccountsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                cards = resource.data.map { it.toPresentation() },
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = resource.errorMessage) }
                        _uiEvents.send(
                            FromUiEvents.ShowError(
                                resource.errorMessage ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loader -> {
                        _state.update { it.copy(isLoading = resource.isLoading) }
                    }
                }
            }
        }
    }

    private fun selectAccount(card: Card) {
        viewModelScope.launch {
            _state.update { it.copy(selectedCard = card) }
            _uiEvents.send(FromUiEvents.DismissBottomSheet)
        }
    }
}