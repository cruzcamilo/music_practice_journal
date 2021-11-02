package com.example.musicpracticejournal.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.musicpracticejournal.EventObserver
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.databinding.FragmentPracticeBinding
import com.example.musicpracticejournal.formatToString
import com.example.musicpracticejournal.practicefragments.PracticeFragment
import com.example.musicpracticejournal.screens.HomeFragment.Companion.MUSIC_FRAGMENT_KEY
import com.example.musicpracticejournal.secondsToMinutesSeconds
import com.example.musicpracticejournal.setBpmInformation
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel


class PracticeFragment : Fragment() {

    private lateinit var binding: FragmentPracticeBinding
    private lateinit var practiceFragment: PracticeFragment
    private lateinit var navController: NavController
    private var practiceTimesInSecs:Long? = null
    private val viewModel by viewModels<MusicPracticeViewModel> {
        MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository, (requireActivity().application as MusicPracticeApplication).timerUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        practiceFragment = requireArguments().getParcelable(MUSIC_FRAGMENT_KEY)!!
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
        navController = Navigation.findNavController(binding.root)
        practiceTimesInSecs = practiceFragment.practiceTime.toLong() * 60
        setValuesOnViews()
        handleListeners()
        observeViewModel()
        setupReviewBtn()
        setupNavigation()
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
            viewModel.resetTimer(practiceFragment.practiceTime.toLong())
        }
    }

    private fun setupReviewBtn() {
        binding.btnSaveReview.setOnClickListener {
            viewModel.reviewPracticeFragment()
        }
    }

    private fun setupNavigation() {
        viewModel.reviewFragmentEvent.observe(viewLifecycleOwner, EventObserver{
            val bundle = bundleOf(MUSIC_FRAGMENT_KEY to practiceFragment.id)
            navController.navigate(R.id.action_practiceFragment_to_reviewFragment, bundle)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setValuesOnViews() {
        val title = "${practiceFragment.author} - ${practiceFragment.name}"
        binding.tvMusicFragmentTitle.text = title
        binding.tvCurrentTempo.text = practiceFragment.currentTempo.setBpmInformation(requireContext())
        binding.tvTargetTempo.text = practiceFragment.targetTempo.setBpmInformation(requireContext())
        binding.tvTotalTime.text = practiceFragment.totalPracticeTimeInSeconds.secondsToMinutesSeconds()
        binding.tvLastPractice.text = practiceFragment.updated?.formatToString()?:getString(
            R.string.no_data)
        binding.tvTimer.text = "${practiceFragment.practiceTime}:00"
    }
}