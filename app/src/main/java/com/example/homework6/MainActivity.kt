package com.example.homework6

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.place.text = "Andes Mountain"
        binding.location.text = "South,America"
        binding.price.text = "$230"
        binding.overview.text = "OverView"
        binding.details.text = "Details"
        binding.duration.text = " 8 hours"
        binding.temperature.text = " 16 C"
        binding.rating.text = " 4.5"

        binding.description.text = "This vast mountain range is renowned for its remarkable diversity in terms of topography and climate. It features towering peaks, active volcanoes, deep canyons, expansive plateaus...."

    }
}