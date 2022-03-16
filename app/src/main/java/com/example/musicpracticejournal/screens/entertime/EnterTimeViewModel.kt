package com.example.musicpracticejournal.screens.entertime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicpracticejournal.util.TimeInputUtil
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterTimeViewModel @Inject constructor() : ViewModel(), NumberInput.OnInputListener {

    val event = LiveEvent<Event.ToHomeScreen>()
    val inputTime =  MutableLiveData("00:00")

    override fun onInput(input: String) {
        val currentInputTime = inputTime.value?:""
        inputTime.value = TimeInputUtil.append(input, currentInputTime)
    }

    override fun onDelete() {
        val currentInputTime = inputTime.value?:""
        inputTime.value = TimeInputUtil.delete(currentInputTime)
    }

    fun setTime() {
        event.value = inputTime.value?.let { Event.ToHomeScreen(it) }
    }

    sealed class Event {
        class ToHomeScreen(val time: String) : Event()
    }
}