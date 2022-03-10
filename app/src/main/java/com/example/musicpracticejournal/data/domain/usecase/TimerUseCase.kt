package com.example.musicpracticejournal.data.domain.usecase

import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.util.secondsToMinutesSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

class TimerUseCase @Inject constructor(private val timerScope: CoroutineScope) {

    private var _timerValueFlow = MutableStateFlow("")
    val timerValueFlow: Flow<String> = _timerValueFlow

    private var _timerState = MutableStateFlow(TimerStateEnum.STOPPED)
    val timerState : Flow<TimerStateEnum>
        get() = _timerState

    private var job: Job? = null

    fun start(totalSeconds: Long) {
        _timerState.value = TimerStateEnum.ACTIVE
        if (job == null && _timerState.value == TimerStateEnum.ACTIVE) {
            _timerState.value = TimerStateEnum.ACTIVE
            job = timerScope.launch {
                initTimer(totalSeconds)
                    .collect {  _timerValueFlow.emit(it)  }
            }
        }
    }

    fun pause() {
        _timerState.value = TimerStateEnum.PAUSED
        cancelTimer()
    }

    fun reset(totalSeconds: Long) {
        _timerState.value = TimerStateEnum.STOPPED
        _timerValueFlow.value = totalSeconds.secondsToMinutesSeconds()
        cancelTimer()
    }

    private fun cancelTimer(){
        job?.cancel()
        job = null
    }

    private fun initTimer(totalSeconds: Long): Flow<String> =
        (totalSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(totalSeconds) }
            .conflate()
            .transform { remainingSeconds: Long ->
                val f = DecimalFormat("00")
                val min = remainingSeconds / 60
                val sec = remainingSeconds % 60
                val remainingTime = f.format(min) + ":" + f.format(sec)
                emit(remainingTime)
            }
}