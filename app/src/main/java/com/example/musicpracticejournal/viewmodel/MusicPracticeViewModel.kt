package com.example.musicpracticejournal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.musicpracticejournal.TimerUseCase
import com.example.musicpracticejournal.data.source.local.MusicPracticeRepository
import com.example.musicpracticejournal.timeStringToSeconds

class MusicPracticeViewModel(private val repository: MusicPracticeRepository, private val timerUseCase: TimerUseCase): ViewModel() {

    private val _timerSeconds =  MutableLiveData<Long>()
    val timerSeconds : LiveData<Long> = _timerSeconds

    val timerCurrentTime = timerUseCase.timerValueFlow.asLiveData()
    val timerState = timerUseCase.timerState.asLiveData()

    fun startTimer(totalTime: Long)  {
        timerUseCase.start(totalTime)
    }

    fun pauseTimer() {
        timerUseCase.pause()
        _timerSeconds.value = timerCurrentTime.value!!.timeStringToSeconds()
    }

    fun resetTimer(totalMinutes: Long) {
        val totalSeconds = totalMinutes * 60
        _timerSeconds.postValue(totalSeconds)
        timerUseCase.reset(totalSeconds)
    }
}