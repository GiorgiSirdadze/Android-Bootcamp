package com.example.shemajamebeli3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebeli3.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var matrixAdapter: MatrixAdapter
    private lateinit var recyclerView: RecyclerView

    private var currentPlayer = CellState.X // start with X player
    private var cells = mutableListOf<Cell>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dimension = arguments?.getInt("dimension")
        recyclerView = binding.gameItems
        val gridLayoutManager = dimension?.let { GridLayoutManager(requireContext(), it) }
        recyclerView.layoutManager = gridLayoutManager


        matrixAdapter = MatrixAdapter()

        // setup the adapter with a list of empty cells
        dimension?.let { dim ->
            val numberOfCells = dim * dim
            cells = MutableList(numberOfCells) { index ->
                Cell(index, CellState.EMPTY)
            }
            matrixAdapter.submitList(cells)
            recyclerView.adapter = matrixAdapter
        }


        matrixAdapter.setOnCellClickListener { index ->
            makeMove(index)
        }
    }

    private fun makeMove(index: Int) {
        // if the cell is already filled, return
        if (cells[index].state != CellState.EMPTY) return

        // update the cell with the current players symbol
        cells[index].state = currentPlayer
        matrixAdapter.notifyItemChanged(index)

        // check for a winner or draw
        if (checkWinner()) {
            Toast.makeText(context, "$currentPlayer Wins!", Toast.LENGTH_SHORT).show()
            goBackToConfigurationFragment()
        } else if (checkDraw()) {
            Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
            goBackToConfigurationFragment()
        } else {
            currentPlayer = if (currentPlayer == CellState.X) CellState.O else CellState.X
        }
    }

    private fun checkWinner(): Boolean {
        val dimension = Math.sqrt(cells.size.toDouble()).toInt()
        // check rows
        for (i in 0 until dimension) {
            if (cells.subList(i * dimension, (i + 1) * dimension).all { it.state == currentPlayer }) return true
        }

        // check columns
        for (i in 0 until dimension) {
            if ((0 until dimension).all { cells[i + it * dimension].state == currentPlayer }) return true
        }

        // check diagonals
        if ((0 until dimension).all { cells[it * (dimension + 1)].state == currentPlayer }) return true
        if ((0 until dimension).all { cells[(it + 1) * (dimension - 1)].state == currentPlayer }) return true

        return false
    }

    private fun checkDraw(): Boolean {
        //all field are checked and no winner founded
        return cells.all { it.state != CellState.EMPTY } && !checkWinner()
    }


    private fun goBackToConfigurationFragment() {
        val configurationFragment = ConfigurationFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, configurationFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
