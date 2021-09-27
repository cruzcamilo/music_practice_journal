package com.example.musicpracticejournal.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicpracticejournal.databinding.FragmentCollectionBinding


class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }
}