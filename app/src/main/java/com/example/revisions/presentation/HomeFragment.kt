package com.example.revisions.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revisions.databinding.FragmentHomeBinding
import com.example.revisions.adapter.PostAdapter
import com.example.revisions.adapter.StoryAdapter
import com.example.revisions.resource.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val postViewModel: PostViewModel by viewModels()
    private val storyViewModel: StoryViewModel by viewModels()

    private lateinit var postAdapter: PostAdapter
    private lateinit var storyAdapter: StoryAdapter

    override fun start() {
        setupRecyclerViews()
        observeViewModels()
        fetchData()
    }

    private fun setupRecyclerViews() {
        storyAdapter = StoryAdapter()
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = storyAdapter
        }

        postAdapter = PostAdapter()
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            postViewModel.postItems.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        postAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", "Error: ${resource.errorMessage}")
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            storyViewModel.storyItem.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        storyAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", "Error: ${resource.errorMessage}")
                    }
                }
            }
        }
    }

    private fun fetchData() {
        postViewModel.fetchPostItems()
        storyViewModel.fetchStoryItems()
    }
}
