package com.example.homework12

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.homework12.databinding.FragmentStatusBinding

class StatusFragment : Fragment() {
    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getInt("orderId")

        binding.confirmButton.setOnClickListener {
            val selectedStatus = when {
                binding.deliveredOption.isChecked -> "DELIVERED"
                binding.canceledOption.isChecked -> "CANCELED"
                else -> "PENDING"
            }

            // send the selected status and order ID back to OrdersFragment
            setFragmentResult("statusRequest", Bundle().apply {
                putString("status", selectedStatus)
                putInt("orderId", orderId ?: -1)
            })

            parentFragmentManager.popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
