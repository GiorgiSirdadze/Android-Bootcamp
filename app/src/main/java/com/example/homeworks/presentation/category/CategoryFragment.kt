package com.example.homeworks.presentation.category

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworks.databinding.FragmentCategoryBinding
import com.example.homeworks.presentation.adapter.CategoryAdapter
import com.example.homeworks.presentation.fragment.BaseFragment
import com.example.homeworks.utils.collect
import com.example.homeworks.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun start() {
        setupRecyclerView()
        setupSearch()
        observeState()
        observeEvents()
        fetchInitialData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }
    }

    private fun observeState() {
        collect(viewModel.state) { state ->
            updateUi(state)
        }
    }

    private fun updateUi(state: CategoryState) {
        manageProgressBar(state.loader)
        state.categories.let { categories ->
            Log.d("UI Update", "Updating UI with ${categories.size} items -> ${categories.map { it.name }}")
            categoryAdapter.submitList(categories)
        }
        state.error?.let { errorMessage -> binding.root.showSnackBar(errorMessage) }
    }

    private fun observeEvents() {
        collect(viewModel.uiEvents) { event ->
            when (event) {
                is CategoryUiEvents.ShowError -> {
                    binding.root.showSnackBar(event.errorMessage)
                }
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onEvent(event = CategoryEvent.SearchQuery(query = newText ?: ""))
                return true
            }
        })
    }

    private fun fetchInitialData() {
        viewModel.onEvent(event = CategoryEvent.SearchQuery(""))
    }

   private fun manageProgressBar(isLoader : Boolean){
       binding.progressBar.isVisible = isLoader
   }

}
