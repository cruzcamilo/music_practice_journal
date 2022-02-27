package com.example.musicpracticejournal.screens.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.Event
import com.example.musicpracticejournal.data.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import com.example.musicpracticejournal.timeStringToSeconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: MusicPracticeRepository,
    private val timerUseCase: TimerUseCase
    ): ViewModel() {

    private val date = SimpleDateFormat("dd-MM-yyyy").format(Date())

    private val _timerSeconds =  MutableLiveData<Long>()
    val timerSeconds : LiveData<Long> = _timerSeconds

    private val _lastPracticeDate =  MutableLiveData<String>()
    val lastPracticeDate : LiveData<String> = _lastPracticeDate

    val timerCurrentTime = timerUseCase.timerValueFlow.asLiveData()
    val timerState = timerUseCase.timerState.asLiveData()

    private val _reviewFragmentEvent = MutableLiveData<Event<Unit>>()
    val reviewFragmentEvent: LiveData<Event<Unit>> = _reviewFragmentEvent

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

    fun reviewPracticeFragment() {
        _reviewFragmentEvent.value = Event(Unit)
    }

    fun saveLastPracticeDate(fragmentId: Long) = viewModelScope.launch {
        repository.updatePracticeDate(date, fragmentId)
        if(_lastPracticeDate.value == null) _lastPracticeDate.postValue(date)
    }
}