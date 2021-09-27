package com.example.musicpracticejournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.musicpracticejournal.databinding.FragmentMainScreenBinding
import com.example.musicpracticejournal.viewmodel.MainActivityViewModelFactory
import com.example.musicpracticejournal.viewmodel.MusicPracticeViewModel
import kotlinx.coroutines.Dispatchers


class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    lateinit var viewModel: MusicPracticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), MainActivityViewModelFactory((requireActivity().application as MusicPracticeApplication).repository, Dispatchers.IO))
            .get(MusicPracticeViewModel::class.java)

        binding.fabButton.setOnClickListener {
                navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_mainScreenFragment_to_createFragment)
            }
        }
}