package com.example.homeworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworks.databinding.FragmentCompletedBinding

class CompletedFragment : Fragment() {

    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderAdapter: OrderAdapter

    private val orderList = listOf(
        Order(1, "Lawson Chair", R.drawable.chair, "$120",  "Red", "Qty = 1"),
        Order(2, "Wooden Chair", R.drawable.wood_chair, "$80",  "Black", "Qty = 1"),
        Order(3, "Mini BookShelf", R.drawable.chair, "$350",  "Space Gray", "Qty = 1"),
        Order(4, "Mirrored Reflector", R.drawable.mirrored, "$150",  "White", "Qty = 1")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setup()
    }

    private fun setup(){
        orderAdapter = OrderAdapter(OrderStatus.COMPLETED) {
            val reviewBottomSheet = ReviewFragment()
            reviewBottomSheet.show(parentFragmentManager, "ReviewBottomSheet")
        }

        binding.recyclerViewCompleted.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
        orderAdapter.submitList(orderList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
