package com.example.homework12

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework12.databinding.OrderItemsBinding

class ItemsDiffUtil : DiffUtil.ItemCallback<Orders>(){
    override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean {
        return oldItem == newItem
    }

}
class OrdersAdapter : ListAdapter <Orders, OrdersAdapter.OrdersViewHolder>(ItemsDiffUtil()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersAdapter.OrdersViewHolder {
        val binding = OrderItemsBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.OrdersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class OrdersViewHolder(private val binding: OrderItemsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind (orders : Orders){
            with(binding){
                trackingNumber.text = orders.trackNumber
                orderDate.text = orders.orderDate
                quantity.text = orders.quantity
                subtotal.text = orders.subtotal
                status.text = orders.status

                status.setTextColor(
                    when (orders.status) {
                        "DELIVERED" -> itemView.context.getColor(R.color.green)
                        "CANCELED" -> itemView.context.getColor(R.color.red)
                        else -> itemView.context.getColor(R.color.orange)
                    }
                )


                detailButton.setOnClickListener {
                    val statusFragment = StatusFragment()

                    // pass the order ID to StatusFragment
                    val bundle = Bundle().apply {
                        putInt("orderId", orders.id) // Pass the ID
                    }
                    statusFragment.arguments = bundle

                    (itemView.context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.orderFragment, statusFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
        }

    }
}