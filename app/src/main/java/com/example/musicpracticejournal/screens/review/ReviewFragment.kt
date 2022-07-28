package com.example.musicpracticejournal.screens.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.BaseFragment
import com.example.musicpracticejournal.data.db.entity.Review
import com.example.musicpracticejournal.databinding.FragmentReviewBinding
import com.example.musicpracticejournal.screens.home.HomeFragment
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import javax.inject.Inject

class ReviewFragment : BaseFragment() {

    private lateinit var binding: FragmentReviewBinding
    private var fragmentId: Long? = null
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ReviewViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReviewViewModel::class.java)
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