package com.example.musicpracticejournal.screens.entertime

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterTimeViewModel @Inject constructor() : ViewModel(), NumberInput.OnInputListener {

    //TODO: implement on input. Add initial value for timer textview

    override fun onInput(input: String) {
        Log.e("onInput", input)
    }

    override fun onDelete() {
        Log.e("onInput", "Implement Delete")
    }

    sealed class Event {
        object ToHomeScreen : Event()
    }
}