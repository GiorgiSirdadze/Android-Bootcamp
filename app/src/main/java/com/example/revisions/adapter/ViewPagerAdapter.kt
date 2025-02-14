package com.example.revisions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.revisions.data.ItemDto
import com.example.revisions.databinding.ItemCardBinding

class ViewPagerAdapter : ListAdapter<ItemDto, ViewPagerAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        Glide.with(binding.imageCover.context)
            .load(item.cover)
            .centerCrop()
            .into(binding.imageCover)

        binding.textLocation.text = item.location
        binding.textReaction.text = item.reactionCount.toString()
        binding.textTitle.text = item.title
        binding.textPrice.text = item.price
        binding.ratingBar.rating = item.rate?.toFloat() ?: 0f
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ItemDto>() {
            override fun areItemsTheSame(oldItem: ItemDto, newItemDto: ItemDto): Boolean {
                return oldItem.id == newItemDto.id
            }

            override fun areContentsTheSame(oldItemDto: ItemDto, newItemDto: ItemDto): Boolean {
                return oldItemDto == newItemDto
            }
        }
    }
}
