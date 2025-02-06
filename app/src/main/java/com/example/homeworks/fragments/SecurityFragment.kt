package com.example.homeworks.fragments

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentSecurityBinding

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(FragmentSecurityBinding::inflate) {

    private val correctPasscode = listOf(0, 9, 3, 4)
    private val enteredPasscode = mutableListOf<Int>()

    override fun start() {
        setupKeypad()
    }

    @SuppressLint("NewApi")
    private fun setupKeypad() {
        val keypadButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        keypadButtons.forEach { button ->
            button.setOnClickListener {
                val digit = button.text.toString().toInt()
                handleDigitInput(digit)
            }
        }

        binding.btnDelete.setOnClickListener {
            if (enteredPasscode.isNotEmpty()) {
                enteredPasscode.removeLast()
                updateIndicators()
            }
        }

        binding.btnFingerPrint.setOnClickListener {
            showToast("Fingerprint doesnt work yet")
        }
    }

    private fun handleDigitInput(digit: Int) {
        if (enteredPasscode.size < 4) {
            enteredPasscode.add(digit)
            updateIndicators()
        }

        if (enteredPasscode.size == 4) {
            checkPasscode()
        }
    }

    private fun updateIndicators() {
        val indicators = listOf(
            binding.Indicators.getChildAt(0),
            binding.Indicators.getChildAt(1),
            binding.Indicators.getChildAt(2),
            binding.Indicators.getChildAt(3)
        )

        indicators.forEachIndexed { index, view ->
            view.background = if (index < enteredPasscode.size)
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_selected)
            else
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_unselected)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkPasscode() {
        if (enteredPasscode == correctPasscode) {
            showToast("Success")
            resetPasscode()
        } else {
            showToast("Incorrect Passcode")
        }
    }

    private fun resetPasscode() {
        enteredPasscode.clear()
        updateIndicators()
    }

    private fun showToast(message: String) {
        requireContext().let { android.widget.Toast.makeText(it, message, android.widget.Toast.LENGTH_SHORT).show() }
    }
}
