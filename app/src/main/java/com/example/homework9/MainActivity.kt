package com.example.homework9

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.homework9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            showSignUpFragment()
        }
    }


    private fun showSignUpFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(android.R.id.content, SignUpFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}