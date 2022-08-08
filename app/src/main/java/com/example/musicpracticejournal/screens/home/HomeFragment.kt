package com.example.musicpracticejournal.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.common.BaseFragment
import com.example.musicpracticejournal.databinding.FragmentHomeBinding
import com.example.musicpracticejournal.practicefragments.EntryAdapter

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var entryAdapter: EntryAdapter

    companion object {
        const val MUSIC_FRAGMENT_KEY = "music_fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerAdapter()
        setupNavigation()
        setupObservers()
    }

    private fun setupRecyclerAdapter() {
        entryAdapter = EntryAdapter { showPracticeScreen(it) }
        binding?.rvFragments?.adapter = entryAdapter
    }

    private fun setupObservers() {
        viewModel.entries.observe(viewLifecycleOwner) { musicFragments ->
            entryAdapter.submitList(musicFragments)
        }
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewModel.Event.CreateScreen ->
                    findNavController().navigate(HomeFragmentDirections.toCreateFragment())
            }
        }
    }

    private fun showPracticeScreen(entryId: Long) {
        findNavController().navigate(HomeFragmentDirections.toPracticeFragment(entryId))
    }
}