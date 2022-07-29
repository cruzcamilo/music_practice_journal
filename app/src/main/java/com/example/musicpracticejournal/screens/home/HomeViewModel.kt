package com.example.musicpracticejournal.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.domain.usecase.RetrieveEntriesUseCase
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicPracticeRepository,
    private val retrieveEntriesUseCase: RetrieveEntriesUseCase
    ) :
    ViewModel() {

    private val _entries = MutableLiveData<List<MusicFragment>>()
    val entries: LiveData<List<MusicFragment>> = _entries

    val emptyImageVisibility: LiveData<Int> =
        mapWithDefault(_entries, View.GONE) { visibleOrGone(it.isNullOrEmpty()) }
    val event = LiveEvent<Event>()

    init {
        viewModelScope.launch {
            retrieveEntriesUseCase.invoke(Unit).collect {
                _entries.value = it
            }
        }
    }

    fun createPracticeFragment() {
        event.value = Event.CreateScreen
    }

    sealed class Event {
        object CreateScreen : Event()
    }

    /**
     * Temporary methods for testing purposes
     * //TODO: Remove when no longer required.
     */
    fun addMockPracticeFragment() = viewModelScope.launch {
        repository.saveMock()
    }

    fun deleteAllMusicFragments() = viewModelScope.launch {
        repository.deleteAllMusicFragments()
    }
}