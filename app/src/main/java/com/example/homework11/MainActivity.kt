package com.example.homework11

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val locationAdapter by lazy {
        LocationAdapter()
    }
    private val locationList = listOf(
        Location(1,  "SBI Building, street3", "My Office"),
        Location(2,  "SBI Building, street4", "Conference Room"),
        Location(3,  "SBI Building, street5", "Lobby")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()

        binding.addAdressButton.setOnClickListener {
            showAddAddressFragment()
        }
    }
    private fun setUp(){
        binding.locationItems.layoutManager = LinearLayoutManager(this)
        binding.locationItems.adapter = locationAdapter
        locationAdapter.submitList(locationList)
    }


    fun addNewLocation(location: Location) {
        locationAdapter.addItem(location)
    }

   private fun showAddAddressFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, AddAddressFragment())
        transaction.addToBackStack(null)
        transaction.commit()
   }




}
