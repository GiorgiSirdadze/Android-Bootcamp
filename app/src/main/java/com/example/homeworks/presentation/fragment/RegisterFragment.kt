//package com.example.homeworks.presentation.fragment
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.fragment.app.setFragmentResult
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.example.homeworks.R
//import com.example.homeworks.databinding.FragmentRegisterBinding
//import com.example.homeworks.resource.Resource
//import com.example.homeworks.presentation.viewmodel.RegisterViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.launch
//
//
//@AndroidEntryPoint
//class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
//
//    private val registerViewModel: RegisterViewModel by viewModels()
//
//    override fun start() {
//        backButton()
//
//        setupRegisterButton()
//
//        observeRegistrationState()
//    }
//
//    /**
//     * Set up the click listener for the register button.
//     */
//    private fun setupRegisterButton() {
//        binding.registerButton.setOnClickListener {
//            val email = binding.emailInput.text.toString().trim()
//            val password = binding.passwordInput.text.toString().trim()
//            val repeatPassword = binding.repeatPasswordInput.text.toString().trim()
//
//            when {
//                email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
//                    Toast.makeText(requireContext(),
//                        getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show()
//                }
//                password != repeatPassword -> {
//                    Toast.makeText(requireContext(),
//                        getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
//                }
//                else -> {
//                    registerViewModel.register(email, password)
//                }
//            }
//        }
//    }
//
//    /**
//     * Observe the registration result from the ViewModel and handles navigation or errors.
//     */
//    private fun observeRegistrationState() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            registerViewModel.registerState.collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        handleSuccessfulRegistration()
//                    }
//                    is Resource.Error -> {
//                        Toast.makeText(requireContext(), resource.errorMessage ?: getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
//                    }
//                    else -> {}
//                }
//            }
//        }
//    }
//
//    /**
//     * Handle actions after a successful registration, including sending data back to LoginFragment.
//     */
//    private fun handleSuccessfulRegistration() {
//        val email = binding.emailInput.text.toString().trim()
//        val password = binding.passwordInput.text.toString().trim()
//
//        setFragmentResult(
//            "register_result",
//            Bundle().apply {
//                putString("email", email)
//                putString("password", password)
//            }
//        )
//        Toast.makeText(requireContext(),
//            getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
//
//        findNavController().navigateUp()
//    }
//
//    /**
//     * Handle back button navigation.
//     */
//    private fun backButton(){
//        binding.backButton.setOnClickListener{
//            findNavController().navigateUp()
//        }
//    }
//}
