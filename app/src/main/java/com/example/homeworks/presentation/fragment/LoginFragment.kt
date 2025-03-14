package com.example.homeworks.presentation.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homeworks.databinding.FragmentLoginBinding
import com.example.homeworks.presentation.event.LoginUiEvents
import com.example.homeworks.presentation.viewmodel.LoginViewModel
import com.example.homeworks.utils.launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        observeViewModel()
        handleUserInput()
    }

    private fun observeViewModel() {
        launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is LoginUiEvents.ActivateLoginButton -> {
                        binding.loginButton.isEnabled = event.isEnabled
                    }
                    is LoginUiEvents.ShowEmailError -> {
                        binding.emailInput.error = "Invalid email format"
                    }
                }
            }
        }
    }

    private fun handleUserInput() {
        binding.emailInput.doAfterTextChanged { text ->
            viewModel.onEmailChanged(text.toString())
        }
    }
}

