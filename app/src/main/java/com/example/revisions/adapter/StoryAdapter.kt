package com.example.revisions.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.revisions.data.StoryItemDto
import com.example.revisions.databinding.StoryItemBinding

class StoryAdapter : ListAdapter<StoryItemDto, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val storyItem = getItem(position)
        holder.bind(storyItem)
    }

    class StoryViewHolder(private val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: StoryItemDto) {
            binding.itemText.text = storyItem.title
            Glide.with(binding.itemImage.context)
                .load(storyItem.cover)
                .into(binding.itemImage)
        }
    }

    class StoryDiffCallback : DiffUtil.ItemCallback<StoryItemDto>() {
        override fun areItemsTheSame(oldItem: StoryItemDto, newItem: StoryItemDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoryItemDto, newItem: StoryItemDto): Boolean {
            return oldItem == newItem
        }
    }
}
