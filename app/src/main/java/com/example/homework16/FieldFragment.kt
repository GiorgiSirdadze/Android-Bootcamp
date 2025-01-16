package com.example.homework16

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework16.databinding.FragmentFieldBinding

class FieldFragment : BaseFragment<FragmentFieldBinding>(FragmentFieldBinding::inflate) {


    private val fieldViewModel: FieldViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldViewModel.fieldGroups.observe(viewLifecycleOwner) { fieldGroups ->
            val adapter = CardsAdapter(fieldViewModel)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.submitList(fieldGroups)
        }

        binding.registerButton.setOnClickListener {

            val inputData = fieldViewModel.inputData.value
            Log.d("FieldFragment", "Input data: $inputData")
            if (inputData.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_fieldFragment_to_informationFragment)
            }
        }
    }
}
