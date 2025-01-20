package com.example.revisions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.revisions.databinding.MessageItemBinding

class ChatAdapter : ListAdapter<ChatItem, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    class ChatViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatItem: ChatItem) {
            if (!chatItem.image.isNullOrEmpty()) {
                Glide.with(binding.profilePicture.context)
                    .load(chatItem.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.profilePicture)
            } else {
                binding.profilePicture.setImageResource(R.drawable.ic_launcher_background)
            }

            binding.senderName.text = chatItem.owner
            binding.messageText.text = when (chatItem.lastMessageType) {
                LastMessageType.TEXT -> chatItem.lastMessage
                LastMessageType.FILE -> "📁 File sent"
                LastMessageType.VOICE -> "🎤 Voice message"
            }
            binding.messageTime.text = chatItem.lastActive

            if (chatItem.unreadMessages > 0) {
                binding.messageBadge.visibility = View.VISIBLE
                binding.messageBadge.text = chatItem.unreadMessages.toString()
            } else {
                binding.messageBadge.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = MessageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ChatDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem == newItem
        }
    }

}
