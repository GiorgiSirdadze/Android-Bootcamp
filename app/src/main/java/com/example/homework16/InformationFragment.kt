package com.example.homework16

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.homework16.databinding.FragmentInformationBinding

class InformationFragment : BaseFragment<FragmentInformationBinding>(FragmentInformationBinding::inflate) {


    private val fieldViewModel: FieldViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldViewModel.inputData.observe(viewLifecycleOwner) { inputData ->
            Log.d("InformationFragment", "Observed data: $inputData")
            if (inputData.isNotEmpty()) {
                val displayText = buildString {
                    inputData.forEach { (hint, value) ->
                        append("$hint: $value\n")
                    }
                }
                binding.information.text = displayText
            }
            }
        }
    }

