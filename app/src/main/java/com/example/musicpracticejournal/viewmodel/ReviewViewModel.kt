package com.example.musicpracticejournal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.Event
import com.example.musicpracticejournal.data.source.local.MusicPracticeRepository
import com.example.musicpracticejournal.reviews.Review
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: MusicPracticeRepository): ViewModel() {

    private val _saveReviewEvent = MutableLiveData<Event<Unit>>()
    val saveReviewEvent: LiveData<Event<Unit>> = _saveReviewEvent

    fun saveReview(review: Review) = viewModelScope.launch {
        repository.saveReview(review)
        _saveReviewEvent.value = Event(Unit)
    }
}