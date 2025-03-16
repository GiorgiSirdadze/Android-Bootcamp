package com.example.homeworks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworks.domain.preference_key.PreferenceKeys
import com.example.homeworks.domain.usecase.GetDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> get() = _email

    fun loadEmail() {
        viewModelScope.launch {
            getDataUseCase.invoke(PreferenceKeys.EMAIL, "").collect { savedEmail ->
                _email.value = savedEmail // Update the state with the saved email
            }
        }
    }
}
