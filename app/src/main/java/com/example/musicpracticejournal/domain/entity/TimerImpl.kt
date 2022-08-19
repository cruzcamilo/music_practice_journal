package com.example.musicpracticejournal.domain.entity

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.timeStringToSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class TimerImpl (private val timerScope: CoroutineScope): Timer {

    private var initTime = DEFAULT_TIMER_VALUE
    private var timerValueFlow = MutableStateFlow(DEFAULT_TIMER_VALUE)
    private var timerState = MutableStateFlow(TimerStateEnum.STOPPED)
    private var currentTimeSeconds = initTime.timeStringToSeconds()
    private var job: Job? = null

    override fun getCurrentTime(): Flow<String> = timerValueFlow
    override fun getCurrentState(): TimerStateEnum = timerState.value
    override fun getStateAsLiveData(): LiveData<TimerStateEnum> = timerState.asLiveData()

    override fun start() {
        timerState.value = TimerStateEnum.ACTIVE
        job = timerScope.launch {
            getTimerFlow().collect {  timerValueFlow.emit(it)  }
        }
    }

    override fun getTimerFlow(): Flow<String> =
        (currentTimeSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(currentTimeSeconds) }
            .conflate()
            .transform { remainingSeconds: Long ->
                emit(TimeInputUtil.secondsToTime(remainingSeconds))
            }
            .onCompletion {
                if(timerValueFlow.value == "0") timerState.value = TimerStateEnum.FINISHED
            }

    override fun playPause() {
        if (timerState.value == TimerStateEnum.ACTIVE) pause()
        else start()
    }

    override fun pause() {
        timerState.value = TimerStateEnum.PAUSED
        currentTimeSeconds = timerValueFlow.value.timeStringToSeconds()
        cancel()
    }

    override fun reset() {
        timerState.value = TimerStateEnum.STOPPED
        currentTimeSeconds = initTime.timeStringToSeconds()
        cancel()
    }

    override fun setInitTime(time: String) {
        initTime = time
        currentTimeSeconds = time.timeStringToSeconds()
    }

    override fun cancel() {
        job?.cancel()
        job = null
    }
}