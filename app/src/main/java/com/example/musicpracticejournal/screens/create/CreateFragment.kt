package com.example.musicpracticejournal.screens.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.musicpracticejournal.MainActivity
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.PracticeFragment
import com.example.musicpracticejournal.databinding.FragmentCreateBinding
import com.example.musicpracticejournal.practicefragments.PracticeStateEnum
import com.example.musicpracticejournal.practicefragments.PracticeTimeEnum
import com.example.musicpracticejournal.practicefragments.PracticeTypeEnum
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var navController: NavController
    private val viewModel:CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        initializeSpinners()
        observeViewModelResult()
        handleListeners()
    }

    private fun initializeSpinners() {
        val typeAdapter: ArrayAdapter<PracticeTypeEnum> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, PracticeTypeEnum.values()
        )
        binding.spinnerPracticeType.setText(PracticeTypeEnum.SONG.type)
        binding.spinnerPracticeType.setAdapter(typeAdapter)

        val minutesAdapter: ArrayAdapter<PracticeTimeEnum> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, PracticeTimeEnum.values()
        )
        binding.spinnerCreatePracticeTime.setAdapter(minutesAdapter)
        val stateArray = arrayOf(PracticeStateEnum.ACTIVE.state, PracticeStateEnum.QUEUED.state)
        val practiceFragmentStateAdapter: ArrayAdapter<String> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, stateArray
        )
        binding.spinnerPracticeState.setText(PracticeStateEnum.ACTIVE.state)
        binding.spinnerPracticeState.setAdapter(practiceFragmentStateAdapter)
    }

    private fun handleListeners() {
        binding.btnSaveMusicFragment.setOnClickListener {
            saveMusicalFragment()
        }

        binding.spinnerPracticeType.addTextChangedListener {
            binding.textInputLayoutCreateType.error = null
        }

        binding.etCreateName.addTextChangedListener {
            binding.textInputLayoutCreateName.error = null
        }

        binding.etCreateAuthor.addTextChangedListener {
            binding.textInputLayoutSongTechnique.error = null
        }

        binding.spinnerCreatePracticeTime.addTextChangedListener {
            binding.textInputLayoutPracticeTime.error = null
        }

        binding.spinnerPracticeState.addTextChangedListener {
            binding.textInputLayoutPracticeState.error = null
        }

        binding.spinnerPracticeType.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                when (p0.toString()) {
                    PracticeTypeEnum.SONG.type -> {
                        binding.textInputLayoutSongTechnique.hint = getString(R.string.song_hint)
                        binding.textInputLayoutCreateName.hint = getString(R.string.section_hint)
                    }
                    PracticeTypeEnum.EXERCISE.type -> {
                        binding.textInputLayoutSongTechnique.hint = getString(R.string.technique_hint)
                        binding.textInputLayoutCreateName.hint = getString(R.string.name_hint)
                    }
                }
            }
        })
    }

    private fun saveMusicalFragment() {
        val type = binding.spinnerPracticeType.text?.toString() ?: ""
        val author = binding.etCreateAuthor.text?.toString() ?: ""
        val name = binding.etCreateName.text?.toString() ?: ""
        val practiceTime = binding.spinnerCreatePracticeTime.text.toString()
        val practiceState = binding.spinnerPracticeState.text.toString()
        viewModel.insert(PracticeFragment(type, author, name, practiceTime, practiceState))
    }

    private fun observeViewModelResult() {
        viewModel.createMusicFragmentState.observe(viewLifecycleOwner) {
            when (it) {
                is CreateViewModel.CreateMusicFragmentState.CreateMusicFragmentSaved -> navController.popBackStack()
                is CreateViewModel.CreateMusicFragmentState.CreateMusicFragmentWithInvalidFields -> handleErrorFields(
                    it.fields
                )
            }
        }
    }

    private fun handleErrorFields(fields: List<Pair<String, Int>>) {
        val validationFields: Map<String, TextInputLayout> = initValidationFields()
        fields.forEach {
            val stringErrorMessage = getString(it.second)
            validationFields[it.first]?.error = stringErrorMessage
        }
    }

    private fun initValidationFields() = mapOf(
        CreateViewModel.INPUT_TYPE.first to binding.textInputLayoutCreateType,
        CreateViewModel.INPUT_NAME.first to binding.textInputLayoutCreateName,
        CreateViewModel.INPUT_AUTHOR.first to binding.textInputLayoutSongTechnique,
        CreateViewModel.INPUT_PRACTICE_TIME.first to binding.textInputLayoutPracticeTime,
    )
}