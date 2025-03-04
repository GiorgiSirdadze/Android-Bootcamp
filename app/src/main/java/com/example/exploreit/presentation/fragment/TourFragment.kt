package com.example.exploreit.presentation.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exploreit.R
import com.example.exploreit.data.mapper.Tour
import com.example.exploreit.databinding.FragmentTourBinding
import com.example.exploreit.presentation.adapter.TourAdapter
import com.example.exploreit.presentation.viewmodel.TourViewModel
import com.example.exploreit.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class TourFragment : BaseFragment<FragmentTourBinding>(FragmentTourBinding::inflate) {

    private val tourViewModel: TourViewModel by viewModels()
    private val tourAdapter: TourAdapter by lazy {
        TourAdapter { tour -> showTourDetails(tour) }
    }

    private lateinit var allTours: List<Tour>
    private lateinit var filteredTours: List<Tour>

    private val db = FirebaseFirestore.getInstance()

    override fun start() {
        setupRecyclerView()
        observeTourItems()
        fetchUsername()
        tourViewModel.fetchTourItems()
        setupSearchFilter()
        setupFilterButtons()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tourAdapter
        }
    }

    private fun observeTourItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            tourViewModel.tourItems.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        allTours = resource.data
                        filteredTours = allTours
                        tourAdapter.submitList(filteredTours)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(resource.errorMessage)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchUsername() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val userRef = db.collection("users").document(userId)
            userRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val username = documentSnapshot.getString("username") ?: "User"
                        binding.helloUsername.text = getString(R.string.welcome, username)
                    }
                }
                .addOnFailureListener {
                    binding.helloUsername.text = getString(R.string.welcome)
                }
        }
    }

    private fun setupSearchFilter() {
        binding.filterButton.setOnClickListener {
            val query = binding.searchBar.text.toString().trim()
            filteredTours = if (query.isNotEmpty()) {
                allTours.filter {
                    it.title.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true)
                }
            } else {
                allTours
            }
            tourAdapter.submitList(filteredTours)
        }
    }

    private fun setupFilterButtons() {
        val filterButtons = listOf(
            binding.filterAll to { allTours },
            binding.filterPopular to { allTours.filter { it.rating.toInt() == 5 } },
            binding.filterLowPrice to { allTours.filter { it.price < 800 } },
            binding.filterBigDuration to { allTours.filter { it.duration == "7 days" } }
        )

        filterButtons.forEach { (button, filterAction) ->
            button.setOnClickListener {
                filteredTours = filterAction()
                tourAdapter.submitList(filteredTours)
                highlightSelectedButton(button)
            }
        }
    }

    private fun highlightSelectedButton(selectedButton: Button) {
        val filterButtons = listOf(binding.filterAll, binding.filterPopular, binding.filterLowPrice, binding.filterBigDuration)
        filterButtons.forEach { button ->
            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.filter_outline)
        }
        selectedButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.filter_solid)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showTourDetails(tour: Tour) {
        val bottomSheet = TourDetailsBottomSheet.newInstance(tour)

        bottomSheet.setOnBookNowClickListener(object : TourDetailsBottomSheet.OnBookNowClickListener {
            override fun onBookNowClicked() {
                findNavController().navigate(R.id.action_tourFragment_to_paymentFragment)
            }
        })

        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}
