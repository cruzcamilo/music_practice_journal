package com.example.musicpracticejournal.screens.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.domain.usecase.DeleteAllEntriesUseCase
import com.example.musicpracticejournal.domain.usecase.RetrieveEntriesUseCase
import com.example.musicpracticejournal.domain.usecase.SaveTestEntryUseCase
import com.example.musicpracticejournal.domain.entity.EntryItem
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val saveTestEntryUseCase: SaveTestEntryUseCase,
    private val deleteAllEntriesUseCase: DeleteAllEntriesUseCase,
    private val retrieveEntriesUseCase: RetrieveEntriesUseCase
    ) : ViewModel() {

    private val _entries = MutableLiveData<List<EntryItem>>()
    val entries: LiveData<List<EntryItem>> = _entries

    val emptyImageVisibility: LiveData<Int> =
        mapWithDefault(_entries, View.GONE) { visibleOrGone(it.isEmpty()) }

    private val _event = LiveEvent<Event>()
    val event: LiveData<Event> = _event

    init {
        viewModelScope.launch {
            retrieveEntriesUseCase.invoke(Unit).collect {
                _entries.value = it
            }
        }
    }

    fun goToCreateScreen() {
        _event.value = Event.CreateScreen
    }

    sealed class Event {
        object CreateScreen : Event()
    }

    /**
     * Temporary methods for testing purposes
     * //TODO: Remove when no longer required.
     */
    fun addMockEntry() = viewModelScope.launch {
        saveTestEntryUseCase.invoke(Unit)
    }

    fun deleteAllEntries() = viewModelScope.launch {
        deleteAllEntriesUseCase.invoke(Unit)
    }
}