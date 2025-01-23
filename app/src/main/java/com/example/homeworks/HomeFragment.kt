package com.example.homeworks

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.example.homeworks.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {
        // Display the logged-in email
        displayLoggedInEmail()

        // Set up the logout button click listener
        setupLogoutButton()
    }

    /**
     * Retrieves the logged-in email from arguments and displays it in the UI.
     */
    private fun displayLoggedInEmail() {
        val email = arguments?.getString("email", "Unknown")
        binding.loggedInEmail.text = email
    }

    /**
     * Sets up the click listener for the logout button.
     * Clears the session and navigates back to the LoginFragment.
     */
    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            clearSession()
            navigateToLogin()
        }
    }

    /**
     * Clears the user's session by removing all data from SharedPreferences.
     */
    private fun clearSession() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    /**
     * Navigates back to the LoginFragment and clears the back stack to prevent returning to HomeFragment.
     */
    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_homeFragment_to_loginFragment,
            null,
            androidx.navigation.NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true) // Clear all back stack up to LoginFragment
                .build()
        )
    }
}
