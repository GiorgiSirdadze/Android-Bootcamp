package com.example.homeworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.usecase.LoginUseCase
import com.example.homeworks.domain.usecase.ValidateEmailUseCase
import com.example.homeworks.presentation.event.LoginUiEvents
import com.example.homeworks.presentation.state.LoginState
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
import com.example.homeworks.presentation.mapper.toPresentation

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val loginUseCase : LoginUseCase
) : ViewModel() {

    private val _uiEvents = Channel<LoginUiEvents>()
    val uiEvents get() = _uiEvents.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state : StateFlow<LoginState> get() = _state

    fun validateEmail(email : String){
        viewModelScope.launch {
            _uiEvents.send(LoginUiEvents.ActivateLoginButton(validateEmailUseCase(email = email)))
        }
    }

    fun login(email: String, password : String){
        if(password.isBlank()) return

        viewModelScope.launch {
            loginUseCase(email = email, password = password).collectLatest { resource ->
                when (resource){
                    is Resource.Success ->{
                         _state.update { it.copy(authInfo = resource.data.toPresentation())}
                        _uiEvents.send(LoginUiEvents.NavigateToHomeScreen)
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(error = resource.errorMessage) }
                        _uiEvents.send(LoginUiEvents.ShowError(errorMessage = resource.errorMessage))
                    }
                    is Resource.Loader -> _state.update { it.copy(loader = resource.isLoading) }
                }
            }
        }
    }
}



