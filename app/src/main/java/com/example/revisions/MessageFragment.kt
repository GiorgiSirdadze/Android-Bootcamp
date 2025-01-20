package com.example.revisions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revisions.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        chatAdapter = ChatAdapter()


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }


        val chatItems = chatViewModel.getChatItems()
        chatAdapter.submitList(chatItems)


        binding.filterButton.setOnClickListener {
            val query = binding.searchBar.text.toString().trim()
            if (query.isNotEmpty()) {
                val filteredItems = chatItems.filter {
                    it.owner.contains(query, ignoreCase = true)
                }
                chatAdapter.submitList(filteredItems)
            } else {
                chatAdapter.submitList(chatItems)
            }
        }
    }
}
