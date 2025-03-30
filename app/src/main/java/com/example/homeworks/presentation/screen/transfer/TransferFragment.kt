package com.example.homeworks.presentation.screen.transfer

import com.example.homeworks.BaseFragment
import com.example.homeworks.databinding.FragmentTransferBinding
import com.example.homeworks.presentation.screen.from.FromBottomSheet


class TransferFragment : BaseFragment<FragmentTransferBinding>(FragmentTransferBinding::inflate) {
    override fun start() {

        binding.fromAccountLayout.setOnClickListener {
            val bottomSheet = FromBottomSheet()
            bottomSheet.show(parentFragmentManager, "FromBottomSheetTag")
        }
    }


}