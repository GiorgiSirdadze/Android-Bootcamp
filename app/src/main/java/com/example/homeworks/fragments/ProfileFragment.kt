package com.example.homeworks.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworks.adapter.UserAdapter
import com.example.homeworks.viewmodel.UsersViewModel
import com.example.homeworks.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun start() {
        setupRecyclerView()
        observeViewModel()
        usersViewModel.fetchUsers(1)
    }

    /**
     * Set up the RecyclerView.
     */
    private fun setupRecyclerView() {
        adapter = UserAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /**
     * Observe ViewModel state flows for users and errors.
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.usersState.collect { users ->
                users?.let {
                    adapter = UserAdapter(it)
                    binding.recyclerView.adapter = adapter
                }
            }
        }

    }
}
