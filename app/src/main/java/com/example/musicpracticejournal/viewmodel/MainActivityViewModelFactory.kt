package com.example.musicpracticejournal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicpracticejournal.repository.MusicPracticeRepository

class MainActivityViewModelFactory(private val repository: MusicPracticeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicPracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicPracticeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

