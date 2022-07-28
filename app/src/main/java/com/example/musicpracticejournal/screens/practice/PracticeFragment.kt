package com.example.musicpracticejournal.screens.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.BaseFragment
import com.example.musicpracticejournal.databinding.FragmentPracticeBinding
import com.example.musicpracticejournal.screens.entertime.EnterTimeSheet
import com.example.musicpracticejournal.screens.originaltempo.OriginalTempoSheet
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class PracticeFragment : BaseFragment() {

    private var binding: FragmentPracticeBinding? = null

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: PracticeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PracticeViewModel::class.java)
    }

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
//        viewModel.title.observe(viewLifecycleOwner) {
//            activity?.title = it
//        }
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            when(it) {
                is PracticeViewModel.Event.ToReviewScreen -> {
                    findNavController().navigate(
                        PracticeFragmentDirections.toReviewFragment(it.fragmentId)
                    )
                }
                is PracticeViewModel.Event.EnterCustomTime -> {
                    showTimeInput()
                }
                is PracticeViewModel.Event.OriginalTempo -> {
                    showOriginalTimeSheet(it.fragmentId)
                }
            }
        }
    }

    private fun showTimeInput() {
        findNavController().navigate(PracticeFragmentDirections.toEnterTimeBottomSheet())
        setFragmentResultListener(EnterTimeSheet.TIME_REQUEST_KEY) { _, bundle ->
            viewLifecycleOwner.lifecycleScope.launch {
                val time = bundle.getString(EnterTimeSheet.TIME_RESULT_KEY)
                time?.let { viewModel.setTimerValue(it) }
            }
        }
    }

    private fun showOriginalTimeSheet(fragmentId: Long) {
        val bundle = bundleOf("fragment_id" to fragmentId)
        findNavController().navigate(R.id.originalTempoSheet, bundle)
        setFragmentResultListener(OriginalTempoSheet.ORIGINAL_TEMPO_KEY) { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.refreshAndStartTimer()
            }
        }
    }
}