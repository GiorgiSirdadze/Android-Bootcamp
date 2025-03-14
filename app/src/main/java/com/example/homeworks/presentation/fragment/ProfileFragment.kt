//package com.example.homeworks.presentation.fragment
//
//import android.widget.Toast
//import androidx.core.view.isVisible
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.homeworks.databinding.FragmentProfileBinding
//import com.example.homeworks.data.paging.UserPagingAdapter
//import com.example.homeworks.presentation.viewmodel.UsersViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
//
//    private val usersViewModel: UsersViewModel by viewModels()
//
//    private lateinit var adapter: UserPagingAdapter
//
//    override fun start() {
//        syncUsersAndObservePaging()
//        setupRecyclerView()
//        observePagingData()
//        handleLoadState()
//    }
//
//    /**
//     * Fetch data from API (if needed) and observes paginated data from Room.
//     */
//    private fun syncUsersAndObservePaging() {
//        usersViewModel.syncUsersFromApi()
//    }
//
//    /**
//     * Set up RecyclerView with Paging Adapter.
//     */
//    private fun setupRecyclerView() {
//        adapter = UserPagingAdapter()
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = this@ProfileFragment.adapter
//        }
//    }
//
//    /**
//     * Observe paginated user data from Room and submits it to the adapter.
//     */
//    private fun observePagingData() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            usersViewModel.userPagingFlow.collectLatest { pagingData ->
//                adapter.submitData(pagingData)
//            }
//        }
//    }
//
//    /**
//     * Handle loading states for pagination.
//     */
//    private fun handleLoadState() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest { loadStates ->
//                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
//                        || loadStates.append is LoadState.Loading
//
//                if (loadStates.refresh is LoadState.Error) {
//                    val error = (loadStates.refresh as LoadState.Error).error
//                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
//
//
//
//
//
