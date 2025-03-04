package com.example.exploreit.presentation.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exploreit.databinding.FragmentFavoriteBinding
import com.example.exploreit.presentation.viewmodel.FavoriteViewModel
import com.example.exploreit.presentation.adapter.TourAdapter
import kotlinx.coroutines.launch

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val favoriteAdapter: TourAdapter by lazy {
        TourAdapter {
        }
    }

    override fun start() {
        setupRecyclerView()
        observeFavoriteTours()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
    }

    private fun observeFavoriteTours() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.favoriteTours.collect { tours ->
                favoriteAdapter.submitList(tours)
            }
        }
    }
}
