package com.example.homework11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework11.databinding.AddaddressScreenBinding

class AddAddressFragment : Fragment() {
    private var _binding: AddaddressScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddaddressScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            saveAddress()
        }
    }
    private fun saveAddress() {
        val locationName = binding.enterLocation.text.toString()
        val address = binding.enterAddress.text.toString()
        val id = binding.enterId.text.toString()
        val addressID = id.toInt()

        if (locationName.isNotEmpty() && address.isNotEmpty()) {
            val newLocation = Location(
                id = addressID,
                address = address,
                location = locationName
            )
            (activity as? MainActivity)?.addNewLocation(newLocation)
            parentFragmentManager.popBackStack()
        }

    }
}