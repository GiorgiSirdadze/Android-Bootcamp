package com.example.homework12

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework12.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val orderAdapter by lazy {
        OrdersAdapter()
    }

    private val ordersList = mutableListOf(
        Orders(1, "SE24239423", "22/24/2005", "5", "180$", "PENDING"),
        Orders(2, "SE24239423", "22/24/2005", "11", "180$", "PENDING"),
        Orders(3, "SE24239423", "22/24/2005", "2", "180$", "PENDING"),
        Orders(4, "SE24239423", "22/24/2005", "8", "180$", "PENDING"),
        Orders(5, "SE24239422", "21/24/2005", "4", "240$", "PENDING"),
        Orders(6, "SE24239423", "22/24/2005", "2", "180$", "PENDING"),
        Orders(7, "SE24239424", "23/24/2005", "3", "220$", "PENDING")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()


        with(binding) {
            btnPending.setOnClickListener {
                filterOrders("PENDING")
            }
            btnDelivered.setOnClickListener {
                filterOrders("DELIVERED")
            }
            btnCancelled.setOnClickListener {
                filterOrders("CANCELED")
            }
        }

        // get result from StatusFragment
        setFragmentResultListener("statusRequest") { _, bundle ->
            val status = bundle.getString("status")
            val orderId = bundle.getInt("orderId")

            if (orderId != -1) {
                updateOrderStatus(orderId, status)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUp() {
        with(binding) {
            orderItems.layoutManager = LinearLayoutManager(requireContext())
            orderItems.adapter = orderAdapter
            orderAdapter.submitList(ordersList)
        }
    }

    private fun updateOrderStatus(orderId: Int, status: String?) {
        status?.let {
            // so here just find the order by its ID and update the status
            val index = ordersList.indexOfFirst { it.id == orderId }
            if (index != -1) {
                val updatedList = ordersList.toMutableList()
                updatedList[index].status = it
                ordersList.clear()
                ordersList.addAll(updatedList)

                orderAdapter.submitList(ordersList.toList()) // update the adapter
            }
        }
    }
    private fun filterOrders(status: String) {
        // Update button backgrounds with custom drawables
        with(binding) {
            btnPending.setBackgroundResource(
                if (status == "PENDING") R.drawable.button_selected_background else R.drawable.button_default_background
            )
            btnDelivered.setBackgroundResource(
                if (status == "DELIVERED") R.drawable.button_selected_background else R.drawable.button_default_background
            )
            btnCancelled.setBackgroundResource(
                if (status == "CANCELED") R.drawable.button_selected_background else R.drawable.button_default_background
            )
        }

        val filteredList = ordersList.filter { it.status == status }
        orderAdapter.submitList(filteredList)
    }


}
