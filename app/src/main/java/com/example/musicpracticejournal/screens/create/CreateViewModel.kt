package com.example.musicpracticejournal.screens.create

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.CreateEntryUseCase
import com.example.musicpracticejournal.practicefragments.PracticeTypeEnum
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createEntryUseCase: CreateEntryUseCase,
    private val resourceManager: ResourceManager
    ) : ViewModel() {

    val type = MutableLiveData<String>()
    val author = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val practiceState = MutableLiveData<String>()
    val trackTempo = MutableLiveData(false)
    val saveButtonEnabled = MediatorLiveData<Boolean>()
    val event = LiveEvent<Event.ToHomeScreen>()

    val songTechniqueHint = type.map {
        if (it == PracticeTypeEnum.SONG.type ||it.isNullOrBlank()) {
            resourceManager.getString(R.string.song_hint)
        } else {
            resourceManager.getString(R.string.technique_hint)
        }
    }
    val createNameHint = type.map {
        if (it == PracticeTypeEnum.SONG.type ||it.isNullOrBlank()) {
            resourceManager.getString(R.string.section_hint)
        } else {
            resourceManager.getString(R.string.name_hint)
        }
    }

    init {
        with(saveButtonEnabled) {
            addSource(type) { value = isSaveButtonEnabled() }
            addSource(author) { value = isSaveButtonEnabled() }
            addSource(name) { value = isSaveButtonEnabled() }
            addSource(practiceState) { value = isSaveButtonEnabled() }
        }
    }

    private fun isSaveButtonEnabled(): Boolean {
        val values = listOf(type.value, author.value, name.value, practiceState.value)

        return values.none { it.isNullOrBlank() }
    }

    fun save() {
        viewModelScope.launch {
            createEntryUseCase(
                CreateEntryUseCase.Params(
                    Entry(
                        type.value ?: "",
                        author.value ?: "",
                        name.value ?: "",
                        practiceState.value ?: "",
                        trackTempo.value ?: false
                    )
                )
            )
            event.value = Event.ToHomeScreen
        }
    }

    sealed class Event {
        object ToHomeScreen : Event()
    }
}