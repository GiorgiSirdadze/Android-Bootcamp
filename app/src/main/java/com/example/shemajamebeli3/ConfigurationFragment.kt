package com.example.shemajamebeli3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shemajamebeli3.databinding.FragmentConfigurationBinding



class ConfigurationFragment : Fragment() {


    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStartGame.setOnClickListener {
            val dimension = binding.editTextDimension.text.toString().toIntOrNull()
            if (dimension != null && dimension in 3..5) {

                val gameFragment = GameFragment()
                val bundle = Bundle().apply {
                    putInt("dimension", dimension)
                }
                gameFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFragment, gameFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(context, "Please enter a valid dimension (3-5)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}