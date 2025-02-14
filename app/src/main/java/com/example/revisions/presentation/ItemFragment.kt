package com.example.revisions.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.revisions.adapter.ViewPagerAdapter
import com.example.revisions.databinding.FragmentItemBinding
import com.example.revisions.resource.Resource
import com.example.revisions.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemFragment : BaseFragment<FragmentItemBinding>(FragmentItemBinding::inflate) {

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var adapter: ViewPagerAdapter

    override fun start() {
        setupViewPager()
        observeData()
        viewModel.fetchItems()
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter()
        binding.viewPager.adapter = adapter

        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 3

        binding.viewPager.setPadding(80, 0, 80, 0)

        val recyclerView = binding.viewPager.getChildAt(0) as RecyclerView
        recyclerView.clipToPadding = false
        recyclerView.clipChildren = false
        recyclerView.setPadding(40, 0, 40, 0)


        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(30))
        transformer.addTransformer { page, position ->
            val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
            page.scaleY = scaleFactor
            page.scaleX = scaleFactor
        }

        binding.viewPager.setPageTransformer(transformer)
    }



    private fun observeData() {
        lifecycleScope.launch {
            viewModel.items.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        adapter.submitList(resource.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }


}
