package com.example.homework10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class FilterAdapter(private val filterList: List<Filters>) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private lateinit var mainListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemLick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mainListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterAdapter.FilterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.filter_items,
            parent, false
        )

        return FilterViewHolder(itemView, mainListener)
    }

    override fun onBindViewHolder(holder: FilterAdapter.FilterViewHolder, position: Int) {
        val filters = filterList[position]
        holder.filterType.text = filters.categoryType
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    class FilterViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val filterType: TextView = itemView.findViewById(R.id.filter)

        init {
            itemView.setOnClickListener {
                listener.onItemLick(adapterPosition)
            }
        }
    }
}