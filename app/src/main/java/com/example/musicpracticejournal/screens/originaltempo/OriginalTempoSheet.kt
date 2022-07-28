package com.example.musicpracticejournal.screens.originaltempo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.common.BaseBottomSheetDialogFragment
import com.example.musicpracticejournal.databinding.OriginalTempoSheetBinding
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject

class OriginalTempoSheet : BaseBottomSheetDialogFragment() {

    private var binding: OriginalTempoSheetBinding? = null
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: OriginalTempoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OriginalTempoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OriginalTempoSheetBinding.inflate(inflater, container, false)
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
        setFragmentResult(ORIGINAL_TEMPO_KEY, bundleOf())
        findNavController().navigateUp()
    }

    companion object {
        const val ORIGINAL_TEMPO_KEY = "original"
    }

}