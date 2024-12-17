package com.example.homework8

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.homework8.databinding.ActivityMainBinding

data class User(var firstName: String, var lastName: String, var age: Int, var email: String)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userInfo = mutableListOf<User>()
    private var removedUsers = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activeUser.text = "Active Users: 0"
        binding.removedUser.text = "Removed Users: 0"


        binding.addUserButton.setOnClickListener {
            showErrorMessages()
            addUser()
            userCounterUpdater()
        }

        binding.removeUserButton.setOnClickListener {
            showErrorMessages()
            removeUser()
            userCounterUpdater()
        }
        binding.updateUserButton.setOnClickListener {
            showErrorMessages()
            updateUserInfo()
        }
    }

    private fun showErrorMessages() {
        binding.firstNameError.text =
            if (binding.firstName.text.toString().isEmpty()) "FirsName Field is Empty!" else ""
        binding.lastNameError.text =
            if (binding.lastName.text.toString().isEmpty()) "LastName Field is Empty!" else ""
        binding.ageError.text =
            if (binding.age.text.toString().isEmpty()) "Age field is Empty!" else ""
        binding.emailError.text =
            if (!binding.email.text.toString().contains("@") || binding.email.text.toString()
                    .isEmpty()
            ) "Email Field Empty or Invalid Format!" else ""

    }

    @SuppressLint("SetTextI18n")
    private fun addUser() {
        if (binding.firstNameError.text.isEmpty() &&
            binding.lastNameError.text.isEmpty() &&
            binding.ageError.text.isEmpty() &&
            binding.emailError.text.isEmpty()
        ) {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val age = binding.age.text.toString().toInt()
            val email = binding.email.text.toString()

            val newUser = User(firstName, lastName, age, email)

            if (userInfo.contains(newUser)) {
                binding.message.text = "User Already Exists!"
                binding.message.setTextColor(Color.RED)

            } else {
                userInfo.add(newUser)
                binding.message.text = "User Added Successfully"
                binding.message.setTextColor(Color.GREEN)
                clearSections()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun removeUser() {
        if (binding.firstNameError.text.isEmpty() &&
            binding.lastNameError.text.isEmpty() &&
            binding.ageError.text.isEmpty() &&
            binding.emailError.text.isEmpty()
        ) {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val age = binding.age.text.toString().toInt()
            val email = binding.email.text.toString()

            val newUser = User(firstName, lastName, age, email)

            if (userInfo.contains(newUser)) {
                userInfo.remove(newUser)
                binding.message.text = "User Removed Successfully!"
                binding.message.setTextColor(Color.GREEN)
                removedUsers++
                clearSections()
            } else {
                binding.message.text = "Such User Doesn't Exists"
                binding.message.setTextColor(Color.RED)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun userCounterUpdater() {
        binding.activeUser.text = "Active Users: ${userInfo.size}"
        binding.removedUser.text = "Removed Users: $removedUsers"

    }

    @SuppressLint("SetTextI18n")
    private fun updateUserInfo() {
        if (binding.firstNameError.text.isEmpty() &&
            binding.lastNameError.text.isEmpty() &&
            binding.ageError.text.isEmpty() &&
            binding.emailError.text.isEmpty()
        ) {
            val email = binding.email.text.toString()
            val existingUser = userInfo.find { it.email == email }

            if (existingUser != null) {
                existingUser.firstName = binding.firstName.text.toString()
                existingUser.lastName = binding.lastName.text.toString()
                existingUser.age = binding.age.text.toString().toInt()
                binding.message.text = "User Updated Successfully!"
                binding.message.setTextColor(Color.GREEN)
                clearSections()
            } else {
                binding.message.text = "User Not Found for Update!"
                binding.message.setTextColor(Color.RED)
            }
        }
    }

    private fun clearSections() {
        binding.firstName.text.clear()
        binding.lastName.text.clear()
        binding.age.text.clear()
        binding.email.text.clear()
    }
}




