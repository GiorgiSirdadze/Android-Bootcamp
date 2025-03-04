package com.example.exploreit.presentation.fragment

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.exploreit.R
import com.example.exploreit.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun start() {
        binding.registerButton.setOnClickListener { handleRegister() }
    }

    private fun handleRegister() {
        val fullName = binding.fullNameEt.text.toString().trim()
        val username = binding.usernameEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()
        val repeatPassword = binding.repeatPasswordEt.text.toString().trim()

        if (!validateInputs(fullName, username, email, password, repeatPassword)) return

        registerUser(fullName, username, email, password)
    }

    private fun validateInputs(
        fullName: String, username: String, email: String, password: String, repeatPassword: String
    ): Boolean {
        return when {
            fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
                showToast(getString(R.string.fill_all_fields))
                false
            }
            password != repeatPassword -> {
                showToast(getString(R.string.passwords_do_not_match))
                false
            }
            else -> true
        }
    }

    private fun registerUser(fullName: String, username: String, email: String, password: String) {
        showProgress(true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { saveUserData(fullName, username, email) }
            .addOnFailureListener { handleRegistrationFailure(it) }
    }

    private fun saveUserData(fullName: String, username: String, email: String) {
        val userId = auth.currentUser?.uid ?: return
        val user = mapOf("fullName" to fullName, "username" to username, "email" to email)

        firestore.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                showProgress(false)
                showToast(getString(R.string.registered_successfully))
                navigateToLogin()
            }
            .addOnFailureListener {
                showProgress(false)
                showToast(getString(R.string.failed_to_save_user, it.message))
            }
    }

    private fun handleRegistrationFailure(exception: Exception) {
        showProgress(false)
        showToast(getString(R.string.registration_failed, exception.message))
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.registerButton.isEnabled = !isVisible
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
