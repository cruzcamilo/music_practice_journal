package com.example.musicpracticejournal.domain.entity

import androidx.lifecycle.LiveData
import com.example.musicpracticejournal.common.Constants
import com.example.musicpracticejournal.data.TimerStateEnum
import kotlinx.coroutines.flow.Flow

interface Timer {
    fun start()
    fun pause()
    fun reset()
    fun playPause()
    fun cancel()
    fun getTimerFlow(): Flow<String>
    fun getCurrentTime(): Flow<String>
    fun getCurrentState(): TimerStateEnum
    fun getStateAsLiveData(): LiveData<TimerStateEnum>
    fun setInitTime(time: String = Constants.DEFAULT_TIMER_VALUE)
}