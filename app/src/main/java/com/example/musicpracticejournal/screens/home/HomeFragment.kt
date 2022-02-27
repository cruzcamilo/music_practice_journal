package com.example.musicpracticejournal.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicpracticejournal.EventObserver
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.databinding.FragmentHomeBinding
import com.example.musicpracticejournal.practicefragments.PracticeFragmentAdapter
import com.example.musicpracticejournal.screens.create.CreateFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding : FragmentHomeBinding? = null
    private val viewModel: CreateFragmentViewModel by viewModels()
    private lateinit var practiceFragmentAdapter: PracticeFragmentAdapter

    companion object {
        val MUSIC_FRAGMENT_KEY = "music_fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvFragments?.layoutManager = GridLayoutManager(context, 2)
        practiceFragmentAdapter = PracticeFragmentAdapter { musicFragment->
            viewModel.startPractice(musicFragment)
        }
        binding?.rvFragments?.adapter = practiceFragmentAdapter
        setupFab()
        setupNavigation()
        observePracticeFragmentList()
    }

    private fun observePracticeFragmentList() {
        viewModel.practiceFragments.observe(viewLifecycleOwner) { musicFragments ->
            viewModel.updateEmptyListText(musicFragments)
            practiceFragmentAdapter.bindData(musicFragments)
        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            Log.d("Reviews", reviews.toString())
        }

        viewModel.emptyMessageVisibility.observe(viewLifecycleOwner) {
            binding?.emptyListMessage.apply {
                if (it) {
                    this?.visibility = View.VISIBLE
                    binding?.icEmptyHome?.visibility = View.VISIBLE
                } else {
                    this?.visibility = View.INVISIBLE
                    binding?.icEmptyHome?.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setupFab() {
        binding?.fabButton?.setOnClickListener {
            viewModel.createPracticeFragment()
        }
    }

    private fun setupNavigation() {
        viewModel.createPracticeFragmentEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(R.id.action_home_to_createFragment)
        })
        viewModel.startPracticeEvent.observe(viewLifecycleOwner, EventObserver{
            val bundle = bundleOf(MUSIC_FRAGMENT_KEY to it)
            findNavController().navigate(R.id.action_home_to_practiceFragment, bundle)
        })
    }
}