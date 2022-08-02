package com.example.musicpracticejournal.domain.usecase

import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.secondsToMinutesSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimerUseCase @Inject constructor(private val timerScope: CoroutineScope) {

    private var _timerValueFlow = MutableStateFlow(DEFAULT_TIMER_VALUE)
    val timerValueFlow: Flow<String> = _timerValueFlow

    private var _timerState = MutableStateFlow(TimerStateEnum.STOPPED)
    val timerState : Flow<TimerStateEnum>
        get() = _timerState

    private var job: Job? = null

    fun start(totalSeconds: Long) {
        _timerState.value = TimerStateEnum.ACTIVE
        if (job == null || job?.isCompleted == true) {
            _timerState.value = TimerStateEnum.ACTIVE
            job = timerScope.launch {
                initTimer(totalSeconds)
                    .collect {  _timerValueFlow.emit(it)  }
            }
        }
    }
    private fun initTimer(totalSeconds: Long): Flow<String> =
        (totalSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(totalSeconds) }
            .conflate()
            .transform { remainingSeconds: Long ->
                emit(TimeInputUtil.secondsToTime(remainingSeconds))
            }
            .onCompletion {
                if(_timerValueFlow.value == "0") _timerState.value = TimerStateEnum.FINISHED
            }

    fun pause() {
        _timerState.value = TimerStateEnum.PAUSED
        cancelTimer()
    }

    fun updateTimerState(stateEnum: TimerStateEnum) {
        _timerState.value = stateEnum
    }

    private fun cancelTimer(){
        job?.cancel()
        job = null
    }

    fun reset(totalSeconds: Long) {
        _timerState.value = TimerStateEnum.STOPPED
        _timerValueFlow.value = totalSeconds.secondsToMinutesSeconds()
        cancelTimer()
    }
}