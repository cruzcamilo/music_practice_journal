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
    var fragmentId: Long? = null

    init {
        fragmentId = CurrentTempoSheetArgs.fromSavedStateHandle(savedStateHandle).fragmentId
        with(saveButtonEnabled) {
            addSource(currentTempo) { value = it.isNotEmpty() }
        }
    }

    fun save () {
        viewModelScope.launch {
            fragmentId?.let {
                updateCurrentTempoUseCase.invoke(
                    UpdateCurrentTempoUseCase.Params(
                        currentTempo = currentTempo.value!!.toInt(),
                        id = fragmentId!!
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