package com.example.musicpracticejournal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.MusicFragment
import com.example.musicpracticejournal.repository.MusicPracticeRepository
import kotlinx.coroutines.launch

class MusicPracticeViewModel(private val repository: MusicPracticeRepository): ViewModel() {

    val allMusicFragments : LiveData<List<MusicFragment>> = repository.currentWeekFragments.asLiveData()

    fun insert(musicFragment: MusicFragment) = viewModelScope.launch {
        Log.d("ViewModel", musicFragment.toString())
        //repository.insert(musicFragment)
    }
}