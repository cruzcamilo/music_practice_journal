package com.example.musicpracticejournal.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.BaseFragment
import com.example.musicpracticejournal.databinding.FragmentHomeBinding
import com.example.musicpracticejournal.practicefragments.PracticeFragmentAdapter
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var practiceFragmentAdapter: PracticeFragmentAdapter

    companion object {
        const val MUSIC_FRAGMENT_KEY = "music_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
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
        practiceFragmentAdapter = PracticeFragmentAdapter { showPracticeScreen(it) }
        binding?.rvFragments?.adapter = practiceFragmentAdapter
    }

    private fun setupObservers() {
        viewModel.musicFragments.observe(viewLifecycleOwner) { musicFragments ->
            practiceFragmentAdapter.submitList(musicFragments)
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
        val bundle = bundleOf("fragment_id" to entryId)
        findNavController().navigate(R.id.practiceFragment, bundle)
    }
}