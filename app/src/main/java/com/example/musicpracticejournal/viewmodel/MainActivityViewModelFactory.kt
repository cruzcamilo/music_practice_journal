package com.example.musicpracticejournal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicpracticejournal.TimerUseCase
import com.example.musicpracticejournal.data.source.local.MusicPracticeRepository

class MainActivityViewModelFactory(private val repository: MusicPracticeRepository, private val timerUseCase: TimerUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateFragmentViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MusicPracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicPracticeViewModel(repository, timerUseCase) as T
        }
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

