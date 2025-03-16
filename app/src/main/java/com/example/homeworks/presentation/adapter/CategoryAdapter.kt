package com.example.homeworks.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworks.databinding.ItemCategoryBinding
import com.example.homeworks.presentation.model.Categories

class CategoryAdapter : ListAdapter<Categories, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Categories) {
            // Set category name
            binding.categoryName.text = category.name

            // Set indentation based on depth
            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.leftMargin = category.depth * 32
            binding.root.layoutParams = layoutParams

            // Set orange balls visibility based on depth
            val orangeBalls = listOf(
                binding.orangeBall1,
                binding.orangeBall2,
                binding.orangeBall3,
                binding.orangeBall4
            )

            // Show orange balls up to the depth (maximum 4)
            for (i in 0 until minOf(category.depth, 4)) {
                orangeBalls[i].visibility = View.VISIBLE
            }

            // Hide remaining orange balls
            for (i in minOf(category.depth, 4) until 4) {
                orangeBalls[i].visibility = View.GONE
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }
    }
}