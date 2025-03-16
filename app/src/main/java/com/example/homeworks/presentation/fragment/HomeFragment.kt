package com.example.homeworks.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentHomeBinding
import com.example.homeworks.presentation.viewmodel.HomeViewModel
import com.example.homeworks.domain.usecase.ClearDataUseCase
import com.example.homeworks.utils.collectLatest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var clearDataUseCase: ClearDataUseCase

    override fun start() {
        observe()
        homeViewModel.loadEmail()

        setupLogoutButton()
        navigateToProfiles()
    }

    /**
     * Observe email from HomeViewModel and update the UI.
     */
    private fun observe() {
        collectLatest(homeViewModel.email) { email ->
            binding.loggedInEmail.text = email
        }
    }

    /**
     * Set up the logout button to clear session and navigate to LoginFragment.
     */
    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                clearDataUseCase.invoke()
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
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }

    private fun navigateToProfiles() {
        binding.profilesButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }
}
