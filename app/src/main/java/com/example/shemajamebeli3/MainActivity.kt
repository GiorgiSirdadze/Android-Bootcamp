package com.example.shemajamebeli3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shemajamebeli3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment()

    }

    private fun showFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainFragment.id,ConfigurationFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}