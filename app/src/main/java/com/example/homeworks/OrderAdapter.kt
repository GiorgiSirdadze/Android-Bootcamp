package com.example.homeworks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworks.databinding.OrderItemBinding

class OrderAdapter(private val orderStatus: OrderStatus, private val onItemButtonClick: (Order) -> Unit) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding,onItemButtonClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        when (orderStatus) {
            OrderStatus.ACTIVE -> {
                holder.bindActiveOrder(order)
            }
            OrderStatus.COMPLETED -> {
                holder.bindCompletedOrder(order)
            }
        }
    }

    class OrderViewHolder(private val binding: OrderItemBinding, private val onItemButtonClick: (Order) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindActiveOrder(order: Order) {
            binding.tvProductName.text = order.orderName
            binding.tvColor.text = order.orderColor
            binding.tvPrice.text = order.orderPrice
            binding.tvQuantity.text = order.orderQty
            binding.imageProduct.setImageResource(order.orderImage)
            binding.tvStatus.text = "In Delivery"
        }

        @SuppressLint("SetTextI18n")
        fun bindCompletedOrder(order: Order) {
            binding.tvProductName.text = order.orderName
            binding.tvColor.text = order.orderColor
            binding.tvPrice.text = order.orderPrice
            binding.tvQuantity.text = order.orderQty
            binding.imageProduct.setImageResource(order.orderImage)
            binding.tvStatus.text = "Completed"
            binding.orderBtn.text = "Leave Review"
            binding.orderBtn.setOnClickListener {
                onItemButtonClick(order)
            }
        }
    }

    class OrderDiffUtil : DiffUtil.ItemCallback<Order>() {

        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}
