package com.example.homeworks

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworks.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)


        // Check if a session exists and navigate to HomeFragment if logged in.

        checkSessionAndNavigate(sharedPreferences)


        // Pre-fill email and password fields from registration result.

        handleRegistrationResult()

        // Set up button listeners for login and register actions.

        setupButtonListeners(sharedPreferences)
    }

    /**
     * Check if the user is logged in and navigate to HomeFragment if true.
     */
    private fun checkSessionAndNavigate(sharedPreferences: SharedPreferences) {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val savedEmail = sharedPreferences.getString("email", "")

        if (isLoggedIn) {
            navigateToHome(savedEmail)
        }
    }

    /**
     * Pre-fill login fields with data received from RegisterFragment.
     */
    private fun handleRegistrationResult() {
        setFragmentResultListener("register_result") { _, bundle ->
            val email = bundle.getString("email", "")
            val password = bundle.getString("password", "")

            binding.emailInput.setText(email)
            binding.passwordInput.setText(password)

            Toast.makeText(requireContext(), "Email and password pre-filled", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set up click listeners for the login and register buttons.
     */
    private fun setupButtonListeners(sharedPreferences: SharedPreferences) {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val rememberMeChecked = binding.rememberMe.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(email, password, rememberMeChecked, sharedPreferences)
            }
        }
    }

    /**
     * Perform login and handle navigation or errors.
     */
    private fun performLogin(email: String, password: String, rememberMeChecked: Boolean, sharedPreferences: SharedPreferences) {
        loginViewModel.login(email, password)

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResult.collect { result ->
                result?.let {
                    if (it.isSuccess) {
                        handleSuccessfulLogin(email, rememberMeChecked, sharedPreferences)
                    } else {
                        val error = it.exceptionOrNull()?.message
                        Toast.makeText(requireContext(), error ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Handle successful login, save session if "Remember Me" is checked, and navigate to HomeFragment.
     */
    private fun handleSuccessfulLogin(email: String, rememberMeChecked: Boolean, sharedPreferences: SharedPreferences) {
        Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()

        if (rememberMeChecked) {
            saveSession(sharedPreferences, email)
        }

        navigateToHome(email)
    }

    /**
     * Save session data to SharedPreferences for persistent login.
     */
    private fun saveSession(sharedPreferences: SharedPreferences, email: String) {
        sharedPreferences.edit().apply {
            putBoolean("isLoggedIn", true)
            putString("email", email)
            apply()
        }
    }

    /**
     * Navigate to HomeFragment and pass the logged-in email as an argument.
     */
    private fun navigateToHome(email: String?) {
        findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment,
            Bundle().apply { putString("email", email) }
        )
    }
}
