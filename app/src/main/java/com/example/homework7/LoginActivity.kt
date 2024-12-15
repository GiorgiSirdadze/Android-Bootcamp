package com.example.homework7

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homework7.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = FirebaseAuth.getInstance()

        binding.loginBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            emailField()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun emailField() {
        binding.logEmailError.text = ""
        val email = binding.loginEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.logEmailError.text = "Email Section is Empty!"
        } else if (!email.contains('@')) {
            binding.logEmailError.text = "Invalid Email Format!"
        } else {
            passwordField()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun passwordField() {
        binding.logPasswordError.text = ""
        val password = binding.loginPassword.text.toString().trim()
        if (password.isEmpty()) {
            binding.logPasswordError.text = "Password Section is Empty!"
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()

        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed!", Toast.LENGTH_LONG).show()
                }
            }
    }
}
