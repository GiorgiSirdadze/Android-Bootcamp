package com.example.homeworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.usecase.RegisterUseCase
import com.example.homeworks.domain.usecase.ValidateEmailUseCase
import com.example.homeworks.presentation.event.RegisterUiEvents
import com.example.homeworks.presentation.mapper.toPresentation
import com.example.homeworks.presentation.state.RegisterState
import com.example.homeworks.data.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val registerUseCase : RegisterUseCase
) : ViewModel() {

    private val _uiEvents = Channel<RegisterUiEvents>()
    val uiEvents get() = _uiEvents.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    val state : StateFlow<RegisterState> get() = _state


    fun validateEmail(email : String){
        viewModelScope.launch {
            _uiEvents.send(RegisterUiEvents.ActivateRegisterButton(validateEmailUseCase(email = email)))
        }
    }

    fun register(email: String, password : String){
        if(password.isBlank()) return

        viewModelScope.launch {
            registerUseCase(email = email, password = password).collectLatest { resource ->
                when (resource){
                    is Resource.Success ->{
                        _state.update { it.copy( registerInfo = resource.data.toPresentation())}
                        _uiEvents.send(RegisterUiEvents.RegistrationSuccessful)
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(error = resource.errorMessage) }
                        _uiEvents.send(RegisterUiEvents.ShowError(errorMessage = resource.errorMessage))
                    }
                    is Resource.Loader -> _state.update { it.copy(loader = resource.isLoading) }
                }
            }
        }
    }
}
