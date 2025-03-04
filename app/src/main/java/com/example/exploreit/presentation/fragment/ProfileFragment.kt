package com.example.exploreit.presentation.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.exploreit.R
import com.example.exploreit.data.repository.DataStoreRepository
import com.example.exploreit.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun start() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            fetchUserData(userId)
        }

        setupLogoutButton()
        setupChangeLanguageButton()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchUserData(userId: String) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                document?.takeIf { it.exists() }?.let {
                    val username = it.getString("username") ?: "Unknown"
                    val fullName = it.getString("fullName") ?: "Unknown"

                    binding.usernameTextView.text = getString(R.string.username_profile, username)
                    binding.fullNameTextView.text = getString(R.string.fullname, fullName)
                }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileFragment", "Error fetching user data", e)
            }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dataStoreRepository.clearLoginState()
            }
            auth.signOut()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_profileFragment_to_loginFragment,
            null,
            androidx.navigation.NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        requireActivity().recreate()
    }

    private fun setupChangeLanguageButton() {
        binding.changeLanguageButton.setOnClickListener {
            toggleLanguage()
        }
    }

    private fun toggleLanguage() {
        val newLang = if (Locale.getDefault().language == "en") "ka" else "en"
        setLocale(newLang)
    }
}
