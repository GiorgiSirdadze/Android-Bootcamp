package com.example.homeworks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.homeworks.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the login button
        binding.loginButton.setOnClickListener {
            val email = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            // Call ViewModel's login function
            viewModel.login(email, password)
        }

        // Observe login result
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                if (response.token != null) {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Error: ${response.error}", Toast.LENGTH_SHORT).show()
                }
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
