package com.example.homeworks.presentation.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentRegisterBinding
import com.example.homeworks.presentation.event.RegisterUiEvents
import com.example.homeworks.presentation.viewmodel.RegisterViewModel
import com.example.homeworks.utils.collectLatest
import com.example.homeworks.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        listeners()
        observe()

        backButton()
    }

    private fun listeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val repeatPassword = binding.repeatPasswordInput.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
                    binding.root.showSnackBar(getString(R.string.all_fields_are_required))
                }
                password != repeatPassword -> {
                    binding.root.showSnackBar(getString(R.string.passwords_do_not_match))
                }
                else -> {
                    viewModel.register(email, password)
                }
            }
        }

        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.validateEmail(email = text.toString())
        }
    }

    private fun observe() {
        collectLatest(viewModel.uiEvents) {
            when (it) {
                is RegisterUiEvents.ActivateRegisterButton -> binding.registerButton.isEnabled = it.isEnabled
                is RegisterUiEvents.ShowError -> binding.root.showSnackBar(it.errorMessage)
                is RegisterUiEvents.RegistrationSuccessful -> handleSuccessfulRegistration()
            }
        }
        collectLatest(viewModel.state) { state ->
            manageProgressBar(state.loader)
        }
    }

    private fun manageProgressBar(isLoader: Boolean) {
        binding.progressBar.isVisible = isLoader
    }


/**
     * Handle actions after a successful registration, including sending data back to LoginFragment.
     */
    private fun handleSuccessfulRegistration() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        setFragmentResult(
            "register_result",
            Bundle().apply {
                putString("email", email)
                putString("password", password)
            }
        )
        Toast.makeText(requireContext(),
            getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()

        findNavController().navigateUp()
    }

    /**
     * Handle back button navigation.
     */
    private fun backButton(){
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}
