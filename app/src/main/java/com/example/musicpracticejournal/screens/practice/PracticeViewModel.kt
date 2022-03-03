package com.example.musicpracticejournal.screens.practice

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.ResourceManager
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.extensions.mapWithDefault
import com.example.musicpracticejournal.extensions.visibleOrInvisible
import com.example.musicpracticejournal.util.minsToSeconds
import com.example.musicpracticejournal.util.secondsToMinutesSeconds
import com.example.musicpracticejournal.util.timeStringToSeconds
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    val currentTempo = MutableLiveData<String>()
    val targetTempo = MutableLiveData<String>()
    val totalTime = MutableLiveData<String>()
    val lastPractice = MutableLiveData<String>()
    var timerTime = MutableLiveData<String>()
    private var practiceTimeInSecs: Long? = null
    private var fragmentId: Long? = null
    private var musicFragment: MusicFragment? = null
    private val timerSeconds =  MutableLiveData<Long>()
    private val date = SimpleDateFormat("dd-MM-yyyy").format(Date())

    private val timerState = timerUseCase.timerState.asLiveData()
    val btnStartVisibility =
        mapWithDefault(timerState, View.VISIBLE) { visibleOrInvisible(it != TimerStateEnum.ACTIVE)}
    val btnPauseVisibility =
        mapWithDefault(timerState, View.INVISIBLE) { visibleOrInvisible(it == TimerStateEnum.ACTIVE)}

    val event = LiveEvent<Event>()

    init {
        viewModelScope.launch {
        fragmentId = PracticeFragmentArgs.fromSavedStateHandle(savedStateHandle).fragmentId
            fragmentId?.let {
                musicFragment = repository.getPracticeFragment(it)
                musicFragment?.let {
                    title.value = "${it.author} - ${it.name}"
                    currentTempo.value = setTempoText(it.currentTempo)
                    targetTempo.value = setTempoText(it.targetTempo)
                    totalTime.value = it.totalPracticeTimeInSeconds.secondsToMinutesSeconds()
                    lastPractice.value = it.updated?: resourceManager.getString(R.string.no_data)
                    timerTime.value = "${it.practiceTime}:00"
                    practiceTimeInSecs = it.practiceTime.minsToSeconds()
                    timerSeconds.value = practiceTimeInSecs!!
                }
            }
        }
        viewModelScope.launch {
            timerUseCase.timerValueFlow.collect {
                timerTime.value = it
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

    fun startTimer()  {
        timerSeconds.value?.let {
            timerUseCase.start(it)
        }
        fragmentId?.let { saveLastPracticeDate(it) }
    }

    fun pauseTimer() {
        timerUseCase.pause()
        timerSeconds.value = timerTime.value?.timeStringToSeconds()
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
    }
}