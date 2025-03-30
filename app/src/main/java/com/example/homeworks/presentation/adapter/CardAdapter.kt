package com.example.homeworks.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworks.databinding.CardItemBinding
import com.example.homeworks.presentation.model.Card

class CardAdapter(
    private val onTourClick: (Card) -> Unit
) : ListAdapter<Card, CardAdapter.CardViewHolder>(TourDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val tour = getItem(position)
        holder.bind(tour)
    }

    inner class CardViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(card: Card) {
            binding.apply {
                txtCardNumber.text = card.accountNumber
                txtTitle.text = card.accountName
                txtAmount.text = card.balance.toString()
            }
            binding.root.setOnLongClickListener {
                onTourClick(card)
                true
            }
        }
    }

    class TourDiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }
}