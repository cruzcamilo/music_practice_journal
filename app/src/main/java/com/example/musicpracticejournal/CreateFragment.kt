package com.example.musicpracticejournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.musicpracticejournal.databinding.FragmentCreateBinding


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

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
    }
}