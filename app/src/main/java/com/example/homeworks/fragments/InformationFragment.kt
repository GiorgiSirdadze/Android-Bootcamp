package com.example.homeworks.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.firstOrNull
import com.example.homeworks.databinding.FragmentInformationBinding
import com.example.homeworks.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class InformationFragment :
    BaseFragment<FragmentInformationBinding>(FragmentInformationBinding::inflate) {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Factory(requireContext())
    }

    override fun start() {
        saveButtonLogic()
        readButtonLogic()
    }

    private fun saveButtonLogic() {
        binding.btnSave.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                userViewModel.saveUser(firstName, lastName, email)
            }
        }
    }

    private fun readButtonLogic() {
        binding.btnRead.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val preferences = userViewModel.userData.firstOrNull()
                preferences?.let {
                    val userInfo = """
                    First Name: ${it.firstName}
                    Last Name: ${it.lastName}
                    Email: ${it.email}
                """.trimIndent()
                    binding.tvUserInfo.text = userInfo
                }
            }
        }
    }

}
