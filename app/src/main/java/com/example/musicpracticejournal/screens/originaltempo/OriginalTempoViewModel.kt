package com.example.musicpracticejournal.screens.originaltempo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.domain.usecase.SaveOriginalTempoUseCase
import com.example.musicpracticejournal.screens.viewmodel.SavedStateViewModel
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


class OriginalTempoViewModel @Inject constructor(
    private val saveOriginalTempoUseCase: SaveOriginalTempoUseCase
) : SavedStateViewModel() {

    val event = LiveEvent<Event.ToPracticeScreen>()
    val originalTempo =  MutableLiveData("")

    val tempoInputEnabled =  MutableLiveData(true)
    private var fragmentId: Long? = null


    override fun init(savedStateHandle: SavedStateHandle) {
        fragmentId = savedStateHandle.get<Long>("fragment_id")
    }

    fun saveTargetTempo () {
        viewModelScope.launch {
            fragmentId?.let {
                if (originalTempo.value?.isNotEmpty() == true ) {
                    saveOriginalTempoUseCase.invoke(
                        SaveOriginalTempoUseCase.Params(
                            originalTempo = retrieveOriginalTempo(),
                            id = fragmentId!!
                        )
                    )
                }
            }
        }
        event.value = Event.ToPracticeScreen
    }

    private fun retrieveOriginalTempo(): Int {
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