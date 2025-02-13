package com.example.homeworks.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworks.viewmodel.LoginViewModel
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentLoginBinding
import com.example.homeworks.repository.DataStoreRepository
import com.example.homeworks.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun start() {


        observeLoginState()

        handleRegistrationResult()

        setupButtonListeners()
    }

    /**
     * Observe the saved login state from DataStore and navigate to HomeFragment if logged in.
     */
    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreRepository.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    dataStoreRepository.email.collect { email ->
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            navigateToHome(email)
                        }
                    }
                }
            }
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
        }
    }

    /**
     * Set up click listeners for the login and register buttons.
     */
    private fun setupButtonListeners() {
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
                performLogin(email, password, rememberMeChecked)
            }
        }
    }

    /**
     * Perform login and save session if "Remember Me" is checked.
     */
    private fun performLogin(email: String, password: String, rememberMeChecked: Boolean) {
        loginViewModel.login(email, password)

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginState.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()

                        if (rememberMeChecked) {
                            saveSession(email)
                        }
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            navigateToHome(email)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * Save login state to DataStore.
     */
    private suspend fun saveSession(email: String) {
        dataStoreRepository.saveLoginState(isLoggedIn = true, email = email)
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
