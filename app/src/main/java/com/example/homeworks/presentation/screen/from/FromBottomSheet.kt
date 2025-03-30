package com.example.homeworks.presentation.screen.from

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworks.databinding.FragmentFromBottomSheetBinding
import com.example.homeworks.presentation.adapter.CardAdapter
import com.example.homeworks.presentation.model.Card
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FromBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentFromBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardAdapter: CardAdapter
    private val viewModel: FromViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFromBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(FromEvent.GetAccounts)
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        cardAdapter = CardAdapter { selectedCard ->

        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        state.cards?.let { cards ->
                            cardAdapter.submitList(cards)
                        }
                        if (state.isLoading) {
                            // progressbar
                        }
                    }
                }

                launch {
                    viewModel.uiEvents.collect { event ->
                        when (event) {
                            is FromUiEvents.ShowError -> {

                            }
                            FromUiEvents.DismissBottomSheet -> dismiss()
                        }
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
