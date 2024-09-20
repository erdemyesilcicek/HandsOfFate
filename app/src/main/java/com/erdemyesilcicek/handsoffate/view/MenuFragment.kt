package com.erdemyesilcicek.handsoffate.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.erdemyesilcicek.handsoffate.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playWithOtherButton.setOnClickListener { playWithOtherButtonClicked(it) }
        binding.playWithComputerButton.setOnClickListener { playWithComputerButtonClicked(it) }
    }

    fun playWithOtherButtonClicked(view: View){
        val action = MenuFragmentDirections.actionMenuFragmentToPlayWithOtherFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun playWithComputerButtonClicked(view: View){
        val action = MenuFragmentDirections.actionMenuFragmentToPlayWithComputerFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}