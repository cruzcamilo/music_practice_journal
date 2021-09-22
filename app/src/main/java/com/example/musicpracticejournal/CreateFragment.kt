package com.example.musicpracticejournal

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musicpracticejournal.databinding.FragmentCreateBinding
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MusicPracticeViewModel> {
        MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerTypeArray = arrayOf("Song", "Exercise")
        val typeAdapter: ArrayAdapter<String> = ArrayAdapter(activity as MainActivity, R.layout.dropdown_menu_type_item, spinnerTypeArray)
        binding.createTypeSpinner.setAdapter(typeAdapter)

        val spinnerTimeArray = arrayOf("5", "10", "15", "20")
        val minutesAdapter: ArrayAdapter<String> = ArrayAdapter(activity as MainActivity, R.layout.dropdown_menu_type_item, spinnerTimeArray)
        binding.createPracticeTimeSpinner.setAdapter(minutesAdapter)

        binding.btnSaveMusicFragment.setOnClickListener {
            val type = binding.createTypeSpinner.text.toString()
            val author = binding.createAuthor.text.toString()
            val name = binding.createName.text.toString()
            val practiceTime = binding.createPracticeTimeSpinner.text?.toString()

            val pattern = "yyyy-MM-dd"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date = simpleDateFormat.format(Date())

            val inputValues = arrayOf(type, author, name, practiceTime)
            if (validateInputs(inputValues)) {
                viewModel.insert(MusicFragment(0, author, name, practiceTime!!, date.toString()))
            }
        }
    }

    fun validateInputs(inputValues: Array<String?>): Boolean {
        inputValues.forEach {
            if (TextUtils.isEmpty(it)) {
                Log.d("Create fragment", "Empty fields")
                return false
            }
        }
        return true
    }
}