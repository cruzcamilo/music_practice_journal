package com.example.musicpracticejournal.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.data.MusicFragment
import com.example.musicpracticejournal.databinding.FragmentPracticeBinding
import com.example.musicpracticejournal.screens.HomeFragment.Companion.MUSIC_FRAGMENT_KEY
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel


class PracticeFragment : Fragment() {

    private lateinit var binding: FragmentPracticeBinding
    private lateinit var currentPracticeFragment: MusicFragment
    private val viewModel by viewModels<MusicPracticeViewModel> {
        MainActivityViewModelFactory((requireContext().applicationContext as MusicPracticeApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPracticeFragment = requireArguments().getParcelable(MUSIC_FRAGMENT_KEY)!!
        Log.d("Current Fragment", currentPracticeFragment.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInformationOnViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setInformationOnViews() {
        val title = "${currentPracticeFragment.author} - ${currentPracticeFragment.name}"
        binding.tvMusicFragmentTitle.text = title
        binding.tvCurrentTempo.text = currentPracticeFragment.currentTempo?.toString() ?: "No data"
        binding.tvTargetTempo.text = currentPracticeFragment.targetTempo?.toString() ?: "No data"
        binding.tvTimer.text = "${currentPracticeFragment.practiceTime}:00"
    }
}