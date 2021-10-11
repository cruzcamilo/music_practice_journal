package com.example.musicpracticejournal.screens

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
import com.example.musicpracticejournal.MusicPracticeApplication
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.addInitialZero
import com.example.musicpracticejournal.data.FragmentTypeEnum
import com.example.musicpracticejournal.data.MusicFragment
import com.example.musicpracticejournal.data.PracticeTimeEnum
import com.example.musicpracticejournal.databinding.FragmentCreateBinding
import com.example.musicpracticejournal.screens.common.DatePickerFragment
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicFragmentViewModel
import com.google.android.material.textfield.TextInputLayout


class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MusicFragmentViewModel> {
        MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository, (requireActivity().application as MusicPracticeApplication).timerUseCase)
    }

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
        val typeAdapter: ArrayAdapter<FragmentTypeEnum> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, FragmentTypeEnum.values()
        )
        binding.spinnerCreateType.setText(FragmentTypeEnum.SONG.type)
        binding.spinnerCreateType.setAdapter(typeAdapter)

        val minutesAdapter: ArrayAdapter<PracticeTimeEnum> = ArrayAdapter(
            activity as MainActivity,
            R.layout.dropdown_menu_type_item, PracticeTimeEnum.values()
        )
        binding.spinnerCreatePracticeTime.setAdapter(minutesAdapter)
    }

    private fun handleListeners() {
        binding.btnSaveMusicFragment.setOnClickListener {
            saveMusicalFragment()
        }

        binding.spinnerCreateType.addTextChangedListener {
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

        binding.etPracticeDate.addTextChangedListener {
            binding.textInputLayoutPracticeDate.error = null
        }

        binding.etPracticeDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.spinnerCreateType.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                when (p0.toString()) {
                    FragmentTypeEnum.SONG.type -> {
                        binding.textInputLayoutSongTechnique.hint = getString(R.string.song_hint)
                        binding.textInputLayoutCreateName.hint = getString(R.string.section_hint)
                    }
                    FragmentTypeEnum.EXERCISE.type -> {
                        binding.textInputLayoutSongTechnique.hint = getString(R.string.technique_hint)
                        binding.textInputLayoutCreateName.hint = getString(R.string.name_hint)
                    }
                }
            }
        })
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment.newInstance { _, year, month, day ->
            val formattedDay = day.addInitialZero()
            val formattedMonth = (month + 1).addInitialZero()
            val selectedDate = "$formattedDay/$formattedMonth/$year"
            binding.etPracticeDate.setText(selectedDate)
        }
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun saveMusicalFragment() {
        val type = binding.spinnerCreateType.text?.toString() ?: ""
        val author = binding.etCreateAuthor.text?.toString() ?: ""
        val name = binding.etCreateName.text?.toString() ?: ""
        val practiceTime = binding.spinnerCreatePracticeTime.text?.toString() ?: ""
        val practiceDate = binding.etPracticeDate.text?.toString() ?: ""

        viewModel.insert(MusicFragment(type, author, name, practiceTime, practiceDate))
    }

    private fun observeViewModelResult() {
        viewModel.createMusicFragmentState.observe(viewLifecycleOwner, {
            when (it) {
                is MusicFragmentViewModel.CreateMusicFragmentState.CreateMusicFragmentSaved -> navController.popBackStack()
                is MusicFragmentViewModel.CreateMusicFragmentState.CreateMusicFragmentWithInvalidFields -> handleErrorFields(it.fields)
            }
        })
    }

    private fun handleErrorFields(fields: List<Pair<String, Int>>) {
        val validationFields: Map<String, TextInputLayout> = initValidationFields()
        fields.forEach {
            val stringErrorMessage = getString(it.second)
            validationFields[it.first]?.error = stringErrorMessage
        }
    }

    private fun initValidationFields() = mapOf(
        MusicFragmentViewModel.INPUT_TYPE.first to binding.textInputLayoutCreateType,
        MusicFragmentViewModel.INPUT_NAME.first to binding.textInputLayoutCreateName,
        MusicFragmentViewModel.INPUT_AUTHOR.first to binding.textInputLayoutSongTechnique,
        MusicFragmentViewModel.INPUT_PRACTICE_TIME.first to binding.textInputLayoutPracticeTime,
        MusicFragmentViewModel.INPUT_PRACTICE_DATE.first to binding.textInputLayoutPracticeDate
    )
}