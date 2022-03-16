package com.example.musicpracticejournal.screens.entertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.databinding.CustomTimeSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EnterTimeSheet : BottomSheetDialogFragment() {

    private var binding: CustomTimeSheetBinding? = null
    private val viewModel: EnterTimeViewModel by viewModels()

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