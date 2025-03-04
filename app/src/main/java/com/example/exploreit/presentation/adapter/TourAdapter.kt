package com.example.exploreit.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exploreit.data.mapper.Tour
import com.example.exploreit.databinding.TourItemBinding
import com.example.exploreit.utils.loadWithErrorHandling

class TourAdapter(
    private val onTourClick: (Tour) -> Unit
) : ListAdapter<Tour, TourAdapter.TourViewHolder>(TourDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val binding = TourItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val tour = getItem(position)
        holder.bind(tour)
    }

    inner class TourViewHolder(private val binding: TourItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(tour: Tour) {
            binding.apply {
                tourTitle.text = tour.title
                tourLocation.text = tour.location
                tourPrice.text = "Price: $${tour.price}"
                tourDuration.text = "Duration: ${tour.duration}"
                tourRating.text = tour.rating.toString()
                tourImageView.loadWithErrorHandling(tour.image)
            }
            binding.root.setOnLongClickListener {
                onTourClick(tour)
                true
            }
        }
    }

    class TourDiffCallback : DiffUtil.ItemCallback<Tour>() {
        override fun areItemsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem == newItem
        }
    }
}
