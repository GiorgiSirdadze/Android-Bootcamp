package com.example.homeworks.carditem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworks.databinding.MasterItemBinding
import com.example.homeworks.databinding.VisaItemBinding

class CardAdapter(private val onCardLongClick: (Card) -> Unit) : ListAdapter<Card, RecyclerView.ViewHolder>(
    CardDiffUtil()
) {

    companion object {
        const val TYPE_MASTER = 0
        const val TYPE_VISA = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).cardType == CardType.MASTER) TYPE_MASTER else TYPE_VISA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MASTER) {
            val binding = MasterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            MasterCardViewHolder(binding)
        } else {
            val binding = VisaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            VisaCardViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val card = getItem(position)
        when (holder) {
            is MasterCardViewHolder -> holder.bind(card)
            is VisaCardViewHolder -> holder.bind(card)
        }
    }

    inner class MasterCardViewHolder(private val binding: MasterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.cardNumber.text = card.cardNumber
            binding.cardName.text = card.cardName
            binding.cardExpiry.text = card.expDate

            itemView.setOnLongClickListener {
                onCardLongClick(card)  // pass the clicked card to the callback
                true
            }
        }
    }

    inner class VisaCardViewHolder(private val binding: VisaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.cardNumber.text = card.cardNumber
            binding.cardName.text = card.cardName
            binding.cardExpiry.text = card.expDate

            itemView.setOnLongClickListener {
                onCardLongClick(card)
                true
            }
        }
    }

    class CardDiffUtil : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }

}

