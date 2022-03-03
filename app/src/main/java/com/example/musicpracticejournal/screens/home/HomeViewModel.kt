package com.example.musicpracticejournal.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MusicPracticeRepository) :
    ViewModel() {

    private val _musicFragments: LiveData<List<MusicFragment>> =
        repository.allMusicFragments.asLiveData()
    val musicFragments: LiveData<List<MusicFragment>> = _musicFragments

    val emptyImageVisibility: LiveData<Int> =
        mapWithDefault(musicFragments, View.GONE) { visibleOrGone(it.isNullOrEmpty()) }

    val event = LiveEvent<Event>()

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
