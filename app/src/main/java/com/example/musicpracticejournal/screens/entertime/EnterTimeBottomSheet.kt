package com.example.musicpracticejournal.screens.entertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicpracticejournal.databinding.CustomTimeSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EnterTimeBottomSheet : BottomSheetDialogFragment() {

    private var binding: CustomTimeSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomTimeSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}