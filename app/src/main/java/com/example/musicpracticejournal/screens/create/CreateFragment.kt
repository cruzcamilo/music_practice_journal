package com.example.musicpracticejournal.screens.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.MainActivity
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.BaseFragment
import com.example.musicpracticejournal.databinding.FragmentCreateBinding
import com.example.musicpracticejournal.practicefragments.EntryStateEnum
import com.example.musicpracticejournal.practicefragments.EntryTypeEnum

class CreateFragment : BaseFragment() {

    private var binding: FragmentCreateBinding? = null
    private val viewModel: CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSpinners()
        setupNavigation()
    }

    private fun initializeSpinners() {
        val typeAdapter: ArrayAdapter<EntryTypeEnum> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, EntryTypeEnum.values()
        )
        with (binding?.spinnerPracticeType) {
            this?.setText(EntryTypeEnum.SONG.type)
            this?.setAdapter(typeAdapter)
        }

        val practiceFragmentStateAdapter: ArrayAdapter<String> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item,
            arrayOf(EntryStateEnum.ACTIVE.state, EntryStateEnum.QUEUED.state)
        )
        with(binding?.spinnerPracticeState) {
            this?.setText(EntryStateEnum.ACTIVE.state)
            this?.setAdapter(practiceFragmentStateAdapter)
        }
    }

    private fun setupNavigation() {
        viewModel.event.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}