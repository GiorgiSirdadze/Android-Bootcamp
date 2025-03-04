package com.example.exploreit.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.exploreit.data.mapper.Tour
import com.example.exploreit.databinding.FragmentTourDetailsBottomSheetBinding
import com.example.exploreit.presentation.viewmodel.FavoriteViewModel
import com.example.exploreit.utils.loadWithErrorHandling
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TourDetailsBottomSheet : BottomSheetDialogFragment() {

    interface OnBookNowClickListener {
        fun onBookNowClicked()
    }

    private var _binding: FragmentTourDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var tour: Tour
    private var bookNowClickListener: OnBookNowClickListener? = null

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    fun setOnBookNowClickListener(listener: OnBookNowClickListener) {
        bookNowClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTourDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Tour>("tour")?.let { selectedTour ->
            tour = selectedTour
            populateTourDetails()
        }

        binding.favorite.setOnClickListener {
            favoriteViewModel.addTourToFavorites(tour)
            showToast("${tour.title} added to favorites")
        }

        binding.bookNowBtn.setOnClickListener {
            bookNowClickListener?.onBookNowClicked()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateTourDetails() {
        with(binding) {
            tourTitle.text = tour.title
            tourLocation.text = tour.location
            tourPrice.text = "$${tour.price}"
            tourDescription.text = tour.description

            tourImage.loadWithErrorHandling(tour.image)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(tour: Tour): TourDetailsBottomSheet {
            return TourDetailsBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable("tour", tour)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
