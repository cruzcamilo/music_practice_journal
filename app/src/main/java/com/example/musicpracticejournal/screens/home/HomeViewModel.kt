package com.example.musicpracticejournal.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.EntryRepository
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
    private val repository: EntryRepository,
    private val retrieveEntriesUseCase: RetrieveEntriesUseCase
    ) :
    ViewModel() {

    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>> = _entries

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

    fun goToCreateScreen() {
        event.value = Event.CreateScreen
    }

    sealed class Event {
        object CreateScreen : Event()
    }

    /**
     * Temporary methods for testing purposes
     * //TODO: Remove when no longer required.
     */
    fun addMockEntry() = viewModelScope.launch {
        repository.saveMock()
    }

    fun deleteAllEntries() = viewModelScope.launch {
        repository.deleteAllEntries()
    }
}