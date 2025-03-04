package com.example.exploreit.presentation.fragment

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.exploreit.R
import com.example.exploreit.data.repository.DataStoreRepository
import com.example.exploreit.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun start() {
        auth = FirebaseAuth.getInstance()
        setupUI()
    }

    private fun setupUI() {
        setupLoginButton()
        setupRegisterButton()
        observeLoginState()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.fill_all_fields))
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        showLoadingState(true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                showLoadingState(false)

                if (task.isSuccessful) {
                    handleSuccessfulLogin(email)
                } else {
                    showLoginError(task.exception?.message)
                }
            }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }

    private fun handleSuccessfulLogin(email: String) {
        if (binding.rememberMeCheckBox.isChecked) {
            saveSession(email)
        }
        showToast(getString(R.string.login_successful))
        navigateToTourFragment()
    }

    private fun saveSession(email: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreRepository.saveLoginState(isLoggedIn = true, email = email)
        }
    }

    private fun showLoginError(message: String?) {
        showToast(getString(R.string.login_failed, message))
    }

    private fun navigateToTourFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_tourFragment)
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreRepository.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToTourFragment()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
