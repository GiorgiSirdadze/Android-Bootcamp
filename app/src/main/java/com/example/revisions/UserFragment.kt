package com.example.revisions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revisions.adapter.UserAdapter
import com.example.revisions.data.RetrofitInstance
import com.example.revisions.data.UserDatabase
import com.example.revisions.data.UserRepository
import com.example.revisions.databinding.FragmentUserBinding
import com.example.revisions.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val database by lazy { UserDatabase.getDatabase(requireContext()) }
    private val repository by lazy { UserRepository(database.userDao(), RetrofitInstance.apiService) }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(repository)
    }

    private lateinit var adapter: UserAdapter

    override fun start() {
        setupRecyclerView()
        checkNetworkStatus()
        observeUsers()
        userViewModel.refreshUsers()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.users.collectLatest { users ->
                adapter.setUsers(users)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun checkNetworkStatus() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        val isOnline = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        binding.networkStatus.text = if (isOnline) "You are online" else "You are offline"
    }
}



