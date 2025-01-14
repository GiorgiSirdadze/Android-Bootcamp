package com.example.homeworks.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworks.carditem.CardAdapter
import com.example.homeworks.viewmodel.PaymentViewModel
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private lateinit var viewPager2: ViewPager2
    private lateinit var cardAdapter: CardAdapter

    private val paymentViewModel: PaymentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = view.findViewById(R.id.cardViewPager)

        cardAdapter = CardAdapter { card ->
            showDeleteCardConfirmation(card.cardId)
        }
        viewPager2.adapter = cardAdapter

        // observe the list of cards in the ViewModel
        paymentViewModel.cards.observe(viewLifecycleOwner) { updatedCards ->
            cardAdapter.submitList(updatedCards)
        }

        binding.tvAddNew.setOnClickListener {
            view.findNavController().navigate(R.id.action_paymentFragment_to_addCardFragment)
        }


    }

    private fun showDeleteCardConfirmation(cardId: Int) {
        val bottomSheetFragment = BottomSheetFragment(cardId)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}


