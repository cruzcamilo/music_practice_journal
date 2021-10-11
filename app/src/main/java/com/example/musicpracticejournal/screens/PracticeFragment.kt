package com.example.musicpracticejournal.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.MusicFragment
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.databinding.FragmentPracticeBinding
import com.example.musicpracticejournal.screens.HomeFragment.Companion.MUSIC_FRAGMENT_KEY
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel


class PracticeFragment : Fragment() {

    private lateinit var binding: FragmentPracticeBinding
    private lateinit var currentPracticeFragment: MusicFragment
    private var practiceTimesInSecs:Long? = null
    private val viewModel by viewModels<MusicPracticeViewModel> {
        MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository, (requireActivity().application as MusicPracticeApplication).timerUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPracticeFragment = requireArguments().getParcelable(MUSIC_FRAGMENT_KEY)!!
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
        practiceTimesInSecs = currentPracticeFragment.practiceTime.toLong() * 60
        setValuesOnViews()
        handleListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.timerState.observe(viewLifecycleOwner, {
            when (it) {
                TimerStateEnum.ACTIVE -> {
                    binding.btnStartTimer.visibility = View.INVISIBLE
                    binding.btnPauseTimer.visibility = View.VISIBLE
                }
                else -> {
                    binding.btnStartTimer.visibility = View.VISIBLE
                    binding.btnPauseTimer.visibility = View.INVISIBLE
                }
            }
        })
        viewModel.timerSeconds.observe(viewLifecycleOwner, {
            practiceTimesInSecs = it
        })
        viewModel.timerCurrentTime.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()) binding.tvTimer.text = it
        })
    }

    @SuppressLint("SetTextI18n")
    private fun handleListeners() {
        binding.btnStartTimer.setOnClickListener {
            viewModel.startTimer(practiceTimesInSecs!!)
        }

        binding.btnPauseTimer.setOnClickListener {
            viewModel.pauseTimer()
        }

        binding.btnRestartTimer.setOnClickListener {
            viewModel.resetTimer(currentPracticeFragment.practiceTime.toLong())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setValuesOnViews() {
        val title = "${currentPracticeFragment.author} - ${currentPracticeFragment.name}"
        binding.tvMusicFragmentTitle.text = title
        binding.tvCurrentTempo.text = currentPracticeFragment.currentTempo?.toString() ?: getString(
                    R.string.no_data)
        binding.tvTargetTempo.text = currentPracticeFragment.targetTempo?.toString() ?: getString(
            R.string.no_data)
        binding.tvTimer.text = "${currentPracticeFragment.practiceTime}:00"
    }
}