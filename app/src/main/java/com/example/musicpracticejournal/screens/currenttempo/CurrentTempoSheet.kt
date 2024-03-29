package com.example.musicpracticejournal.screens.currenttempo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.databinding.CurrentTempoSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentTempoSheet : BottomSheetDialogFragment() {

    private var binding: CurrentTempoSheetBinding? = null
    private val viewModel: CurrentTempoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentTempoSheetBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomDialog()
        setupNavigation()
        setupToolbar()
    }


    private fun setupBottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            dismissSheet()
        }
    }

    private fun setupToolbar() {
        binding?.toolbarSheet?.closeBtn?.setOnClickListener {
            dismissSheet()
        }
    }

    private fun dismissSheet() {
        setFragmentResult(CURRENT_TEMPO_KEY, bundleOf())
        findNavController().navigateUp()
    }

    companion object {
        const val CURRENT_TEMPO_KEY = "current"
    }

}