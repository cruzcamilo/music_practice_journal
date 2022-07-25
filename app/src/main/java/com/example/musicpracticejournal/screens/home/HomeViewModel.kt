package com.example.musicpracticejournal.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val repository: MusicPracticeRepository) :
    ViewModel() {

    val musicFragments = MutableLiveData<List<MusicFragment>>()
    val emptyImageVisibility: LiveData<Int> =
        mapWithDefault(musicFragments, View.GONE) { visibleOrGone(it.isNullOrEmpty()) }
    val event = LiveEvent<Event>()

    init {
        viewModelScope.launch {
            repository.getMusicFragments().collect { musicFragments.value = it }
        }
    }

    fun createPracticeFragment() {
        event.value = Event.CreateScreen
    }

    sealed class Event {
        object CreateScreen : Event()
    }

    fun addMockPracticeFragment() = viewModelScope.launch {
        repository.saveMock()
    }

    fun deleteAllMusicFragments() = viewModelScope.launch {
        repository.deleteAllMusicFragments()
    }
}