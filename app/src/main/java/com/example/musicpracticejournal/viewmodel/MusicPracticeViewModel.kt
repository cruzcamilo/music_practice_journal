package com.example.musicpracticejournal.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import com.example.musicpracticejournal.MusicFragment
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.repository.MusicPracticeRepository
import kotlinx.coroutines.launch

class MusicPracticeViewModel(private val repository: MusicPracticeRepository): ViewModel() {

    companion object {
        val INPUT_TYPE = "INPUT_TYPE" to R.string.fragment_practice_time
        val INPUT_NAME = "INPUT_NAME" to R.string.fragment_name
        val INPUT_AUTHOR = "INPUT_AUTHOR" to R.string.fragment_author
        val INPUT_PRACTICE_TIME = "INPUT_PRACTICE_TIME" to R.string.fragment_practice_time
        val INPUT_PRACTICE_DATE = "INPUT_PRACTICE_DATE" to R.string.fragment_practice_date
    }

    val allMusicFragments : LiveData<List<MusicFragment>> = repository.currentWeekFragments.asLiveData()

    sealed class CreateMusicFragmentState {
        class CreateMusicFragmentSaved : CreateMusicFragmentState()
        class CreateMusicFragmentWithInvalidFields(val fields: List<Pair<String, Int>>) :
            CreateMusicFragmentState()
    }

    private val _createMusicFragmentState = MutableLiveData<CreateMusicFragmentState>()
    val createMusicFragmentState: LiveData<CreateMusicFragmentState>
        get() = _createMusicFragmentState

    fun insert(musicFragment: MusicFragment) = viewModelScope.launch {

        if (validateCreateForm(musicFragment)) {
            Log.d("ViewModel", "Inserting fragment")
            _createMusicFragmentState.value = CreateMusicFragmentState.CreateMusicFragmentSaved()
            //repository.insert(musicFragment)
        }
    }

    private fun validateCreateForm(musicFragment: MusicFragment):Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (TextUtils.isEmpty(musicFragment.type)) {
            invalidFields.add(INPUT_TYPE)
        }
        if (TextUtils.isEmpty(musicFragment.name)) {
            invalidFields.add(INPUT_NAME)
        }
        if (TextUtils.isEmpty(musicFragment.author)) {
            invalidFields.add(INPUT_AUTHOR)
        }
        if (TextUtils.isEmpty(musicFragment.practiceTime)) {
            invalidFields.add(INPUT_PRACTICE_TIME)
        }

        if (TextUtils.isEmpty(musicFragment.practiceDate)) {
            invalidFields.add(INPUT_PRACTICE_DATE)
        }

        if (invalidFields.isNotEmpty()) {
            _createMusicFragmentState.value = CreateMusicFragmentState.CreateMusicFragmentWithInvalidFields(invalidFields)
            return false
        }

        return true
    }
}