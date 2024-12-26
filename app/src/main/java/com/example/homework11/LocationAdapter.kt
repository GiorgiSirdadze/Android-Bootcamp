package com.example.homework11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework11.databinding.LocationItemsBinding

class ItemsDiffUtil: DiffUtil.ItemCallback<Location>(){
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }


}
class LocationAdapter : ListAdapter <Location, LocationAdapter.LocationViewHolder>(ItemsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

  inner class LocationViewHolder(private val binding: LocationItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.address.text = location.address
            binding.location.text = location.location

            binding.checkBox.isChecked = false
            binding.editButton.isEnabled = false

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                binding.editButton.isEnabled = isChecked
            }

            binding.root.setOnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val locationToRemove = getItem(position)
                    removeItem(locationToRemove)
                }
                true
            }
        }
    }
    fun addItem(location: Location) {
        val currentList = currentList.toMutableList()
        currentList.add(0, location)
        submitList(currentList)
    }
    private fun removeItem(location: Location) {
        val currentList = currentList.toMutableList()
        currentList.remove(location)
        submitList(currentList)
    }

}
