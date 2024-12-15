package com.example.homework7

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homework7.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authentication: FirebaseAuth

    private var email: String = ""
    private var password: String = ""
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = FirebaseAuth.getInstance()

        binding.secondPart.visibility = View.GONE

        binding.registerBackButton.setOnClickListener {
            if (binding.secondPart.visibility == View.GONE) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.firstPart.visibility = View.VISIBLE
                binding.secondPart.visibility = View.GONE
            }
        }

        binding.nextButton.setOnClickListener {
            fields()
        }

        binding.signupButton.setOnClickListener {
            usernameField()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fields() {
        binding.emailError.text = ""
        email = binding.email.text.toString().trim()
        if (email.isEmpty()) {
            binding.emailError.text = "Email Section is Empty!"
        } else if (!email.contains('@')) {
            binding.emailError.text = "Invalid Email Format!"
        }

        binding.passwordError.text = ""
        password = binding.password.text.toString().trim()
        if (password.isEmpty()) {
            binding.passwordError.text = "Password Section is Empty!"
        }

        if (email.contains('@') && password.isNotEmpty()) {
            binding.secondPart.visibility = View.VISIBLE
            binding.firstPart.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun usernameField() {
        binding.usernameError.text = ""
        username = binding.username.text.toString().trim()

        if (username.isEmpty()) {
            binding.usernameError.text = "Username Section is Empty!"
        } else {
            registerUser()
        }
    }

    private fun registerUser() {
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = authentication.currentUser
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
