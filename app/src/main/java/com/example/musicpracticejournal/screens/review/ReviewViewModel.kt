package com.example.musicpracticejournal.screens.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.Event
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.data.db.entity.Review
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
    private val repository: MusicPracticeRepository
    ) :
    ViewModel() {

    private val _saveReviewEvent = MutableLiveData<Event<Unit>>()
    val saveReviewEvent: LiveData<Event<Unit>> = _saveReviewEvent

    fun saveReview(review: Review) = viewModelScope.launch {
        repository.saveReview(review)
        _saveReviewEvent.value = Event(Unit)
    }
}