package com.example.homeworks.presentation.fragment

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentLoginBinding
import com.example.homeworks.presentation.event.LoginUiEvents
import com.example.homeworks.presentation.viewmodel.LoginViewModel
import com.example.homeworks.utils.collectLatest
import com.example.homeworks.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        listeners()
        observe()
        handleRegistrationResult()
    }

    private fun listeners(){
        binding.loginButton.setOnClickListener {
            viewModel.login(
                email = binding.emailInput.text.toString(),
                password = binding.passwordInput.text.toString()
            )
        }
        binding.emailInput.doOnTextChanged{text, _, _, _, ->
            viewModel.validateEmail(email = text.toString())

        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }
    }

    private fun observe(){
        collectLatest(viewModel.uiEvents){
            when (it){
                is LoginUiEvents.ActivateLoginButton -> binding.loginButton.isEnabled = it.isEnabled
                is LoginUiEvents.NavigateToHomeScreen -> findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment)
                is LoginUiEvents.ShowError -> binding.root.showSnackBar(it.errorMessage)
            }
        }
        collectLatest(viewModel.state) {state ->
            manageProgressBar(state.loader)
        }
    }


    private fun manageProgressBar(isLoader : Boolean){
        binding.progressBar.isVisible = isLoader
    }

    private fun handleRegistrationResult() {
        setFragmentResultListener("register_result") { _, bundle ->
            val email = bundle.getString("email", "")
            val password = bundle.getString("password", "")

            binding.emailInput.setText(email)
            binding.passwordInput.setText(password)
        }
    }

}

