package com.example.homeworks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.homeworks.databinding.FragmentAddCardBinding

class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {

    private val paymentViewModel: PaymentViewModel by activityViewModels()
    private var cardId : Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMasterCard()

        binding.rbMasterCard.setOnClickListener {
            showMasterCard()
        }
        binding.rbVisa.setOnClickListener {
            showVisaCard()
        }

        binding.btnAddCard.setOnClickListener {
            val cardholderName = binding.etCardholderName.text.toString()
            val cardNumber = binding.etCardNumber.text.toString()
            val expiryDate = binding.etExpiryDate.text.toString()
            val cvv = binding.etCVV.text.toString()

            if (cardholderName.isNotEmpty() && cardNumber.isNotEmpty() && expiryDate.isNotEmpty() && cvv.isNotEmpty()) {
                val cardType = if (binding.rbMasterCard.isChecked) {
                    CardType.MASTER
                } else {
                    CardType.VISA
                }

                val newCard = Card(
                    cardId = cardId,
                    cardNumber = cardNumber,
                    cardName = cardholderName,
                    expDate = expiryDate,
                    cardType = cardType
                )
                paymentViewModel.addCard(newCard)

                view.findNavController().navigate(R.id.action_addCardFragment_to_paymentFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            cardId++
        }
    }

    private fun showMasterCard() {
        binding.viewSwitcher.displayedChild = 0
    }

    private fun showVisaCard() {
        binding.viewSwitcher.displayedChild = 1
    }
}
