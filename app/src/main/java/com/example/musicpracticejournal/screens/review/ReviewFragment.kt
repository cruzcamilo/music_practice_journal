package com.example.musicpracticejournal.screens.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.Review
import com.example.musicpracticejournal.databinding.FragmentReviewBinding
import com.example.musicpracticejournal.screens.home.HomeFragment

class ReviewFragment : Fragment() {

    private lateinit var binding: FragmentReviewBinding
    private var fragmentId: Long? = null
    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentId = requireArguments().getLong(HomeFragment.MUSIC_FRAGMENT_KEY)
        Log.d("Fragment review", fragmentId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSaveReview.setOnClickListener {
            saveReview()
        }
        setupNavigation()
    }

    private fun saveReview() {
        val review = Review(binding.ratingBarAccuracy.rating, binding.ratingBarRhythm.rating,
            binding.ratingBarArticulation.rating, binding.ratingBarDynamics.rating,
            binding.etAdditionalNotes.text.toString(), fragmentId!!)
        viewModel.saveReview(review)
    }

    private fun setupNavigation() {
        viewModel.saveReviewEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.home, false)
        }
    }
}