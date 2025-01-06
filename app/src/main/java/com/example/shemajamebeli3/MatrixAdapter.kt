package com.example.shemajamebeli3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebeli3.databinding.MatrixItemBinding

class MatrixAdapter : ListAdapter<Cell, RecyclerView.ViewHolder>(ItemsDiffUtil()) {

    private var onCellClickListener: ((Int) -> Unit)? = null

    fun setOnCellClickListener(listener: (Int) -> Unit) {
        onCellClickListener = listener
    }

    private var currentPlayer: CellState = CellState.X


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MatrixItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatrixItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cell = getItem(position)
        if (holder is MatrixItemViewHolder) {
            holder.bind(cell)
        }
    }
    inner class MatrixItemViewHolder(private val binding: MatrixItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cell:Cell) {
            when (cell.state) {
                CellState.X -> binding.cellImage.setImageResource(R.drawable.x_icon)
                CellState.O -> binding.cellImage.setImageResource(R.drawable.circle_icon)
                CellState.EMPTY -> binding.cellImage.setImageResource(0)
            }

            // update the cell state
            binding.root.setOnClickListener {
                //  check for cell should not be clicked twice
                if (cell.state != CellState.EMPTY) return@setOnClickListener

                // if cell is empty, mark it with current players symbol
                cell.state = currentPlayer

                // switch the current player for the next turn
                currentPlayer = if (currentPlayer == CellState.X) CellState.O else CellState.X

                // notify the adapter that the cell has changed (refresh the view)
                notifyItemChanged(adapterPosition)
            }

            binding.root.setOnClickListener {
                onCellClickListener?.invoke(position)
            }
        }

    }
    class ItemsDiffUtil : DiffUtil.ItemCallback<Cell>() {
        override fun areItemsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: Cell, newItem: Cell): Boolean {
            return oldItem.state == newItem.state
        }
    }
}
