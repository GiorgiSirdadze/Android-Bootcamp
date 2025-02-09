package com.example.homeworks.fragments

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworks.datastore.DataStoreManager
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var dataStoreManager: DataStoreManager

    override fun start() {
        dataStoreManager = DataStoreManager(requireContext())

        displayEmail()

        setupLogoutButton()

        navigateToProfiles()
    }

    /**
     * Display the logged-in email from arguments.
     */
    private fun displayEmail() {
        val email = arguments?.getString("email", "Unknown")
        binding.loggedInEmail.text = email
    }

    /**
     * Set up the logout button to clear session and navigate to LoginFragment.
     */
    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                dataStoreManager.clearLoginState() // Clear session data
                navigateToLogin()
            }
        }
    }

    /**
     * Navigate to LoginFragment and clear the back stack to prevent navigation issues.
     */
    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_homeFragment_to_loginFragment,
            null,
            androidx.navigation.NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true) // Clears all fragments from back stack
                .build()
        )
    }

    private fun navigateToProfiles(){
        binding.profilesButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }
}
