package com.example.homeworks.fragments

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworks.paging.UserPagingAdapter
import com.example.homeworks.databinding.FragmentProfileBinding
import com.example.homeworks.viewmodel.UsersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserPagingAdapter

    override fun start() {
        setupRecyclerView()
        observePagingData()
        handleLoadState()
    }

    private fun setupRecyclerView() {
        adapter = UserPagingAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProfileFragment.adapter
        }
    }

    private fun observePagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.userPagingFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun handleLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                // Show progress bar during initial loading or appending
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                        || loadStates.append is LoadState.Loading

                // Show a toast or handle errors during loading
                if (loadStates.refresh is LoadState.Error) {
                    val error = (loadStates.refresh as LoadState.Error).error
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}