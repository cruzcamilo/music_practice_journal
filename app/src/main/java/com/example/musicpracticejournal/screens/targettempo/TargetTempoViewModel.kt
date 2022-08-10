package com.example.musicpracticejournal.screens.targettempo

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.UpdateTargetTempoUseCase
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrGone
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TargetTempoViewModel @Inject constructor(
    private val updateTargetTempoUseCase: UpdateTargetTempoUseCase,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    var entryId: Long? = null
    val targetTempo =  MutableLiveData("")
    val tempoInputEnabled =  MutableLiveData(true)
    val saveButtonEnabled = MediatorLiveData<Boolean>()
    private val isSwitchEnabled = MutableLiveData(true)

    private val _event = LiveEvent<Event.ToPracticeScreen>()
    val event : LiveData<Event.ToPracticeScreen> get() = _event

    val descriptionText: LiveData<String> = mapWithDefault(
        isSwitchEnabled, resourceManager.getString(R.string.target_tempo_description_disabled)
    )  {
        if (!it) resourceManager.getString(R.string.target_tempo_description_disabled)
        else resourceManager.getString(R.string.target_tempo_description_enabled)
    }
    val textInputVisibility: LiveData<Int> =
        mapWithDefault(isSwitchEnabled, View.VISIBLE) { visibleOrGone(it) }

    init {
        entryId = TargetTempoSheetArgs.fromSavedStateHandle(savedStateHandle).entryId
        with(saveButtonEnabled) {
            addSource(isSwitchEnabled) { value = isSaveButtonEnabled() }
            addSource(targetTempo) { value = isSaveButtonEnabled() }
        }
    }

    private fun isSaveButtonEnabled(): Boolean {
        return if (isSwitchEnabled.value == false) {
            true
        }  else {
            targetTempo.value?.isNotEmpty()!!
        }
    }

    fun save () {
        saveTargetTempo()
        _event.value = Event.ToPracticeScreen
    }

    private fun saveTargetTempo() {
        viewModelScope.launch {
            entryId?.let {
                updateTargetTempoUseCase.invoke(
                    UpdateTargetTempoUseCase.Params(
                        targetTempo = targetTempo.value!!.toInt(),
                        id = entryId!!
                    )
                )
            }
        }
    }

    fun updateSwitchState(isChecked: Boolean) {
        isSwitchEnabled.value = isChecked
        clearTargetTempo(isChecked)
    }

    private fun clearTargetTempo(isChecked: Boolean) {
        if (!isChecked) targetTempo.value = ""
    }

    sealed class Event {
        object ToPracticeScreen : Event()
    }
}