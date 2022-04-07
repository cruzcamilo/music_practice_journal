package com.example.musicpracticejournal.screens.originaltempo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.domain.usecase.chunks.SaveOriginalTempoUseCase
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OriginalTempoViewModel @Inject constructor(
    private val saveOriginalTempoUseCase: SaveOriginalTempoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    val event = LiveEvent<Event.ToPracticeScreen>()
    val originalTempo =  MutableLiveData("")

    val tempoInputEnabled =  MutableLiveData(true)
    var fragmentId: Long? = null

    init {
        fragmentId = OriginalTempoSheetArgs.fromSavedStateHandle(savedStateHandle).fragmentId
    }

    fun saveTargetTempo () {
        viewModelScope.launch {
            fragmentId?.let {
                if (originalTempo.value?.isNotEmpty() == true ) {
                    saveOriginalTempoUseCase.invoke(
                        SaveOriginalTempoUseCase.Params(
                            originalTempo = getOriginalTempo(),
                            id = fragmentId!!
                        )
                    )
                }
            }
        }
        event.value = Event.ToPracticeScreen
    }

    private fun getOriginalTempo(): Int {
        return if (originalTempo.value.isNullOrEmpty()) {
            0
        } else {
            originalTempo.value!!.toInt()
        }
    }

    sealed class Event {
        object ToPracticeScreen : Event()
    }
}