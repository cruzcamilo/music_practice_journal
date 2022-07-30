package com.example.musicpracticejournal.screens.practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.minsToSeconds
import com.example.musicpracticejournal.util.secondsToMinutesSeconds
import com.example.musicpracticejournal.util.timeStringToSeconds
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: MusicPracticeRepository,
    private val timerUseCase: TimerUseCase,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    val title = MutableLiveData<String>()
    private val currentTempo = MutableLiveData<String>()
    private val targetTempo = MutableLiveData<String>()
    //TODO: Check if these fields should be deleted
    private val totalTime = MutableLiveData<String>()
    private val lastPractice = MutableLiveData<String>()

    var timeOnScreen = MutableLiveData<String>()
    private var fragmentId: Long? = null
    private var musicFragment: MusicFragment? = null
    private val timerSeconds =  MutableLiveData<Long>()
    private val date = SimpleDateFormat("dd-MM-yyyy").format(Date())

    private val timerState = timerUseCase.timerState.asLiveData()
    val btnActionImage = map(timerState) {
        if (it != TimerStateEnum.ACTIVE) {
            resourceManager.getDrawable(R.drawable.ic_baseline_play_circle_outline_24)
        } else {
            resourceManager.getDrawable(R.drawable.ic_baseline_pause_circle_outline_24)
        }
    }

    val event = LiveEvent<Event>()

    init {
        fragmentId = PracticeFragmentArgs.fromSavedStateHandle(savedStateHandle).fragmentId
        fragmentId?.let {
            getPracticeFragment(it)
        }
        viewModelScope.launch {
            timerUseCase.timerValueFlow.collect {
                timeOnScreen.value = it
            }
        }
    }

    private fun getPracticeFragment(it: Long) = viewModelScope.launch {
        musicFragment = repository.getPracticeFragment(it)
        musicFragment?.let {
            title.value = "${it.author} - ${it.name}"
            currentTempo.value = setTempoText(it.currentTempo)
            targetTempo.value = setTempoText(it.originalTempo)
            totalTime.value = it.totalPracticeTimeInSeconds.secondsToMinutesSeconds()
            lastPractice.value = it.updated ?: resourceManager.getString(R.string.no_data)
            setTimerValue()
        }
    }

    fun refreshAndStartTimer() {
        fragmentId?.let {
            runBlocking {
                getPracticeFragment(it)
                startTimer()
            }
        }
    }

    private fun setTempoText(tempo: Int?): String {
        return if (tempo == null) {
            resourceManager.getString(R.string.no_data)
        } else {
            resourceManager.getString(R.string.bpm_amount, tempo.toString())
        }
    }

    fun enterCustomTime() {
        event.value = Event.EnterCustomTime
    }

    fun setTimerValue(time: String = DEFAULT_TIMER_VALUE) {
        timeOnScreen.value = TimeInputUtil.secondsToTime(time.timeStringToSeconds())
        timerSeconds.value = time.timeStringToSeconds()
    }

    fun operateTimer() {
        when (timerState.value) {
            TimerStateEnum.STOPPED -> {
                if (musicFragment?.originalTempo == null) {
                    event.value = fragmentId?.let { Event.OriginalTempo(it) }
                } else {
                    startTimer()
                }
            }
            TimerStateEnum.PAUSED -> startTimer()
            TimerStateEnum.ACTIVE -> pauseTimer()
            null -> return
        }
    }

    private fun startTimer() {
        timerSeconds.value?.let { timerUseCase.start(it) }
        fragmentId?.let { saveLastPracticeDate(it) }
    }

    private fun pauseTimer() {
        timerUseCase.pause()
        timerSeconds.value = timeOnScreen.value?.timeStringToSeconds()
    }

    fun resetTimer() {
        musicFragment?.practiceTime?.minsToSeconds()?.let { totalSeconds->
            timerSeconds.value = totalSeconds
            timerUseCase.reset(totalSeconds)
        }
    }

    private fun saveLastPracticeDate(fragmentId: Long) = viewModelScope.launch {
        repository.updatePracticeDate(date, fragmentId)
        if (musicFragment?.updated == null) {
            lastPractice.value = date
        }
    }

    fun toReviewScreen() {
        fragmentId?.let { event.value = Event.ToReviewScreen(it) }
    }

    sealed class Event {
        class ToReviewScreen(val fragmentId: Long) : Event()
        object EnterCustomTime: Event()
        class OriginalTempo(val fragmentId: Long) : Event()
    }
}