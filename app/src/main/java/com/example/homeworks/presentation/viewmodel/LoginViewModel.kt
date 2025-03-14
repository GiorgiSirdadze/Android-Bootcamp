package com.example.homeworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.usecase.ValidateEmailUseCase
import com.example.homeworks.presentation.event.LoginUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _uiEvent = Channel<LoginUiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        val isValid = validateEmailUseCase(newEmail)

        viewModelScope.launch {
            _uiEvent.send(LoginUiEvents.ActivateLoginButton(isValid))
            if (!isValid) {
                _uiEvent.send(LoginUiEvents.ShowEmailError)
            }
        }
    }
}



