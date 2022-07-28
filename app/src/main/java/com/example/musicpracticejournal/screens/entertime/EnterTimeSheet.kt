package com.example.musicpracticejournal.screens.entertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.common.BaseBottomSheetDialogFragment
import com.example.musicpracticejournal.databinding.CustomTimeSheetBinding
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject


class EnterTimeSheet : BaseBottomSheetDialogFragment() {

    private var binding: CustomTimeSheetBinding? = null
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: EnterTimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EnterTimeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomTimeSheetBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomDialog()
        setupInput()
        setupNavigation()
    }


    private fun setupBottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupInput() {
        binding?.numInput?.root?.onInputListener = viewModel
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            val bundle = bundleOf(TIME_RESULT_KEY to it.time)
            setFragmentResult(TIME_REQUEST_KEY, bundle)
            findNavController().navigateUp()
        }
    }

    companion object {
        const val TIME_RESULT_KEY = "time"
        const val TIME_REQUEST_KEY = "time_request_key"
    }
}