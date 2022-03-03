package com.example.musicpracticejournal.screens.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.databinding.FragmentPracticeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeFragment : Fragment() {

    private var binding: FragmentPracticeBinding? = null
    private val viewModel: PracticeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPracticeBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            if (it is PracticeViewModel.Event.ToReviewScreen) {
                findNavController().navigate(
                    PracticeFragmentDirections.actionPracticeFragmentToReviewFragment(it.fragmentId)
                )
            }
        }
    }
}