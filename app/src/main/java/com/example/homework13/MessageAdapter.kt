package com.example.homework13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.IncommingItemBinding
import com.example.homework13.databinding.OutgoingItemBinding

class MessageAdapter : ListAdapter<MessageItem, RecyclerView.ViewHolder>(ItemsDiffUtil()) {


    companion object {
        const val TYPE_INCOMING = 0
        const val TYPE_OUTGOING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).type == MessageType.INCOMING) TYPE_INCOMING else TYPE_OUTGOING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_INCOMING) {
            val binding = IncommingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            IncomingMessageViewHolder(binding)
        } else {
            val binding = OutgoingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            OutgoingMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is IncomingMessageViewHolder) {
            holder.bind(message)
        } else if (holder is OutgoingMessageViewHolder) {
            holder.bind(message)
        }
    }

    inner class IncomingMessageViewHolder(private val binding: IncommingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageItem) {
            binding.tvIncomingMessage.text = message.message
            binding.tvIncomingTime.text = message.date
        }
    }

    inner class OutgoingMessageViewHolder(private val binding: OutgoingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageItem) {
            binding.tvOutgoingMessage.text = message.message
            binding.tvOutgoingTime.text = message.date
        }
    }


    class ItemsDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem == newItem
        }
    }
}

