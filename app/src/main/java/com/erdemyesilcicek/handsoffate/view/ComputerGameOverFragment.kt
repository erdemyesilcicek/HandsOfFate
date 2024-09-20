package com.erdemyesilcicek.handsoffate.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.erdemyesilcicek.handsoffate.R
import com.erdemyesilcicek.handsoffate.databinding.FragmentComputerGameOverBinding
import com.erdemyesilcicek.handsoffate.databinding.FragmentPlayWithComputerBinding
import pl.droidsonroids.gif.GifImageView

class ComputerGameOverFragment : Fragment() {
    private var _binding: FragmentComputerGameOverBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComputerGameOverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView:GifImageView = binding.statusDisplay
        imageView.setImageResource(R.drawable.lose)

        binding.homeButton.setOnClickListener {
            val action = ComputerGameOverFragmentDirections.actionComputerGameOverFragmentToMenuFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}