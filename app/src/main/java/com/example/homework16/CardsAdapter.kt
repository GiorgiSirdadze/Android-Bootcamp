package com.example.homework16

import android.annotation.SuppressLint
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework16.databinding.ItemCardBinding

class CardsAdapter(private val fieldViewModel: FieldViewModel) :
    ListAdapter<List<Field>, CardsAdapter.CardViewHolder>(ItemDiffUtil()) {

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fields: List<Field>) {
            // Clear the container before adding any view
            binding.fieldsContainer.removeAllViews()

            // Dynamically create views based on field type
            fields.forEach { field ->
                when (field.fieldType) {
                    "input" -> {
                        val editText = EditText(binding.root.context).apply {
                            hint = field.hint
                            inputType = when (field.keyboard) {
                                "number" -> InputType.TYPE_CLASS_NUMBER
                                else -> InputType.TYPE_CLASS_TEXT
                            }
                            // Save data when text changes
                            addTextChangedListener {
                                fieldViewModel.saveFieldData(field.hint, text.toString())
                            }
                        }
                        binding.fieldsContainer.addView(editText)
                    }
                    "chooser" -> {
                        val spinner = Spinner(binding.root.context).apply {
                            // Set custom background drawable to the spinner
                            val genderOptions = listOf("Gender", "Male", "Female")

                            // Create an ArrayAdapter for the Spinner
                            val adapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_spinner_dropdown_item,
                                genderOptions
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            this.adapter = adapter

                            // Set listener for when item is selected
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position > 0) {
                                        fieldViewModel.saveFieldData(field.hint, genderOptions[position])
                                    }
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }

                            }
                        }
                        binding.fieldsContainer.addView(spinner)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val fieldGroup = getItem(position)
        holder.bind(fieldGroup)
    }
}

class ItemDiffUtil : DiffUtil.ItemCallback<List<Field>>() {
    override fun areItemsTheSame(oldItem: List<Field>, newItem: List<Field>): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: List<Field>, newItem: List<Field>): Boolean {
        return oldItem == newItem
    }
}
