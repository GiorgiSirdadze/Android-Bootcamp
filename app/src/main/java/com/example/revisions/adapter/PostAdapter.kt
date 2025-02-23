package com.example.revisions.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.revisions.data.PostItem
import com.example.revisions.databinding.PostItemBinding


class PostAdapter :
    ListAdapter<PostItem, PostAdapter.PostItemViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)
    }

    inner class PostItemViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(postItem: PostItem) { // Use PostItem
            binding.userName.text = postItem.owner.fullName
            binding.timestamp.text = postItem.owner.postDate
            binding.postText.text = postItem.shareContent
            binding.likes.text = "${postItem.likes} Likes"
            binding.comments.text = "${postItem.comments} Comments"
            loadImage(binding.profileImage, postItem.owner.profile)
            handleImagesVisibilityAndLoading(postItem.images)
        }

        private fun loadImage(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }

        private fun handleImagesVisibilityAndLoading(images: List<String>?) {

            binding.largeImage.visibility = View.GONE
            binding.smallImage1.visibility = View.GONE
            binding.smallImage2.visibility = View.GONE

            images?.let {
                if (it.isNotEmpty()) {
                    binding.largeImage.visibility = View.VISIBLE
                    loadImage(binding.largeImage, it[0])
                }

                if (it.size > 1) {
                    binding.smallImage1.visibility = View.VISIBLE
                    loadImage(binding.smallImage1, it[1])
                }

                if (it.size > 2) {
                    binding.smallImage2.visibility = View.VISIBLE
                    loadImage(binding.smallImage2, it[2])
                }
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostItem>() { // Use PostItem
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem
        }
    }
}
