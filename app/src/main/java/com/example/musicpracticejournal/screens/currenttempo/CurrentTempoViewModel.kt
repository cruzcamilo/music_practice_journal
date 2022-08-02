package com.example.musicpracticejournal.screens.currenttempo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.domain.usecase.UpdateCurrentTempoUseCase
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTempoViewModel @Inject constructor(
    private val updateCurrentTempoUseCase: UpdateCurrentTempoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    val event = LiveEvent<Event.ToPracticeScreen>()
    val currentTempo =  MutableLiveData("")
    val saveButtonEnabled = MediatorLiveData<Boolean>()
    var entryId: Long? = null

    init {
        entryId = CurrentTempoSheetArgs.fromSavedStateHandle(savedStateHandle).entryId
        with(saveButtonEnabled) {
            addSource(currentTempo) { value = it.isNotEmpty() }
        }
    }

    fun save () {
        viewModelScope.launch {
            entryId?.let {
                updateCurrentTempoUseCase.invoke(
                    UpdateCurrentTempoUseCase.Params(
                        currentTempo = currentTempo.value!!.toInt(),
                        id = entryId!!
                    )
                )
            }
        }
        event.value = Event.ToPracticeScreen
    }

    sealed class Event {
        object ToPracticeScreen : Event()
    }
}