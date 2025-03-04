package com.example.exploreit.presentation.fragment

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.exploreit.R
import com.example.exploreit.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    override fun start() {
        setupPaymentMethods()
        setupPayButton()
    }

    private fun setupPaymentMethods() {
        binding.rbMasterCard.setOnClickListener { showMasterCard() }
        binding.rbVisa.setOnClickListener { showVisaCard() }

        showMasterCard()
    }

    private fun setupPayButton() {
        binding.btnPay.setOnClickListener {
            val cardholderName = binding.etCardholderName.text.toString()
            val cardNumber = binding.etCardNumber.text.toString()
            val expiryDate = binding.etExpiryDate.text.toString()
            val cvv = binding.etCVV.text.toString()

            if (areFieldsValid(cardholderName, cardNumber, expiryDate, cvv)) {
                processPayment()
            } else {
                showToast(getString(R.string.fill_all_fields))
            }
        }
    }

    private fun areFieldsValid(cardholderName: String, cardNumber: String, expiryDate: String, cvv: String): Boolean {
        return cardholderName.isNotEmpty() && cardNumber.isNotEmpty() && expiryDate.isNotEmpty() && cvv.isNotEmpty()
    }

    private fun processPayment() {
        showToast(getString(R.string.payment_successful))
        findNavController().navigateUp()
    }

    private fun showMasterCard() {
        binding.viewSwitcher.displayedChild = 0
    }

    private fun showVisaCard() {
        binding.viewSwitcher.displayedChild = 1
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
