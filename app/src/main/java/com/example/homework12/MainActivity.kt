package com.example.homework12

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.homework12.databinding.ActivityMainBinding

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
        transaction.replace(binding.orderFragment.id,OrdersFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

}