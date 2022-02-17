package com.example.musicpracticejournal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicpracticejournal.data.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.screens.create.CreateFragmentViewModel
import com.example.musicpracticejournal.screens.practice.PracticeViewModel
import com.example.musicpracticejournal.screens.review.ReviewViewModel

class MainActivityViewModelFactory(private val repository: MusicPracticeRepository, private val timerUseCase: TimerUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateFragmentViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(PracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PracticeViewModel(repository, timerUseCase) as T
        }
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

