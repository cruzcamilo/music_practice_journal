package com.example.musicpracticejournal.screens.originaltempo

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.UpdateOriginalTempoUseCase
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OriginalTempoViewModel @Inject constructor(
    private val saveOriginalTempoUseCase: UpdateOriginalTempoUseCase,
    resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    val event = LiveEvent<Event.ToPracticeScreen>()
    val originalTempo =  MutableLiveData("")
    val tempoInputEnabled =  MutableLiveData(true)
    val saveButtonEnabled = MediatorLiveData<Boolean>()
    var entryId: Long? = null

    private val isSwitchEnabled = MutableLiveData(true)

    val descriptionText: LiveData<String> = mapWithDefault(
        isSwitchEnabled, resourceManager.getString(R.string.original_tempo_description_disabled)
    )  {
        if (!it) resourceManager.getString(R.string.original_tempo_description_disabled)
        else resourceManager.getString(R.string.original_tempo_description_enabled)
    }
    val textInputVisibility: LiveData<Int> =
        mapWithDefault(isSwitchEnabled, View.GONE) { visibleOrGone(it) }

    init {
        entryId = OriginalTempoSheetArgs.fromSavedStateHandle(savedStateHandle).entryId
        with(saveButtonEnabled) {
            addSource(isSwitchEnabled) { value = isSaveButtonEnabled() }
            addSource(originalTempo) { value = isSaveButtonEnabled() }
        }
    }

    private fun isSaveButtonEnabled(): Boolean {
        return if (isSwitchEnabled.value == false) {
            true
        }  else {
            originalTempo.value?.isNotEmpty()!!
        }
    }

    fun save () {
        if (!originalTempo.value.isNullOrEmpty()) {
            saveTargetTempo()
        }
        event.value = Event.ToPracticeScreen
    }

    private fun saveTargetTempo() {
        viewModelScope.launch {
            entryId?.let {
                saveOriginalTempoUseCase.invoke(
                    UpdateOriginalTempoUseCase.Params(
                        originalTempo = originalTempo.value!!.toInt(),
                        id = entryId!!
                    )
                )
            }
        }
    }

    fun updateSwitchState(isChecked: Boolean) {
        isSwitchEnabled.value = isChecked
        if (!isChecked) originalTempo.value = ""
    }

    sealed class Event {
        object ToPracticeScreen : Event()
    }
}