package com.example.musicpracticejournal.screens.practice

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.entity.Timer
import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.timeStringToSeconds
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: EntryRepository,
    private val timer: Timer,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val title = MutableLiveData<String>()
    var timeOnScreen = MutableLiveData<String>()
    private val currentTempo = MutableLiveData<String>()
    private val targetTempo = MutableLiveData<String>()
    private var entryId = PracticeFragmentArgs.fromSavedStateHandle(savedStateHandle).entryId
    private lateinit var entry: Entry
    private val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val timerState = timer.getStateAsLiveData()
    val timerValueFlow = timer.getCurrentTime()

    val btnActionImage = map(timerState) {
        if (it != TimerStateEnum.ACTIVE) {
            resourceManager.getDrawable(R.drawable.ic_play_circle)
        } else {
            resourceManager.getDrawable(R.drawable.ic_pause_circle)
        }
    }

    val isPracticeComplete = map(timerState) {
        it == TimerStateEnum.FINISHED
    }

    private val _event = LiveEvent<Event>()
    val event: LiveData<Event> = _event

    fun onStart() {
        getEntry(entryId)
        setTimerValue()
    }

    private fun getEntry(it: Long) = viewModelScope.launch {
        entry = repository.getEntry(it)
        populateUi()
    }

    private fun populateUi() {
        title.value = "${entry.author} - ${entry.name}"
        currentTempo.value = entry.setTempoText(resourceManager.getApplicationContext(), entry.currentTempo)
        targetTempo.value = entry.setTempoText(resourceManager.getApplicationContext(), entry.targetTempo)
    }

    fun setTimerValue(time: String = DEFAULT_TIMER_VALUE) {
        timer.setInitTime(time)
        timeOnScreen.value = TimeInputUtil.secondsToTime(time.timeStringToSeconds())
    }


    fun operateTimer() {
        if (isTargetTempoRequired()) {
            _event.value = Event.TargetTempo(entryId)
        } else {
            playPause()
        }
    }

    private fun isTargetTempoRequired() = entry.targetTempo == null
            && entry.trackTempo && timer.getCurrentState() == TimerStateEnum.STOPPED

    fun playPause() {
        timer.playPause()
    }

    //TODO review. This button is temporarily disabled
    fun resetTimer() {
        timer.reset()
    }

    private fun saveLastPracticeDate(entryId: Long) {
        viewModelScope.launch {
            repository.updatePracticeDate(date, entryId)
        }
    }

    fun finishPractice() {
        if (entry.trackTempo) {
            toCurrentTempoScreen()
        }
        saveLastPracticeDate(entryId)
    }

    fun toReviewScreen() {
        _event.value = Event.ToReviewScreen(entryId)
    }

    private fun toCurrentTempoScreen() {
        _event.value = Event.ToCurrentTempoScreen(entryId)
    }

    fun toCustomTimeScreen() {
        _event.value = Event.EnterCustomTime
    }

    sealed class Event {
        class ToReviewScreen(val entryId: Long) : Event()
        object EnterCustomTime: Event()
        class TargetTempo(val entryId: Long) : Event()
        class ToCurrentTempoScreen(val entryId: Long) : Event()
    }
}