package com.example.homeworks

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworks.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        backButton()

        // Set up the register button click listener
        setupRegisterButton()

        // Observe registration result
        observeRegistrationResult()

    }

    /**
     * Sets up the click listener for the register button.
     */
    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val repeatPassword = binding.repeatPasswordInput.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
                    Toast.makeText(requireContext(),
                        getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show()
                }
                password != repeatPassword -> {
                    Toast.makeText(requireContext(),
                        getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    registerViewModel.register(email, password)
                }
            }
        }
    }

    /**
     * Observes the registration result from the ViewModel and handles navigation or errors.
     */
    private fun observeRegistrationResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.registerResult.collect { result ->
                result?.let {
                    if (it.isSuccess) {
                        handleSuccessfulRegistration()
                    } else {
                        val error = it.exceptionOrNull()?.message
                        Toast.makeText(requireContext(), error ?: getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Handles actions after a successful registration, including sending data back to LoginFragment.
     */
    private fun handleSuccessfulRegistration() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        // Pass data to LoginFragment using setFragmentResult
        setFragmentResult(
            "register_result",
            Bundle().apply {
                putString("email", email)
                putString("password", password)
            }
        )
        Toast.makeText(requireContext(),
            getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()

        // Navigate back to LoginFragment
        findNavController().navigateUp()
    }

    private fun backButton(){
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}
