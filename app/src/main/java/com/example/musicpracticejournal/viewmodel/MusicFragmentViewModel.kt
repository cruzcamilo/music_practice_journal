package com.example.musicpracticejournal.viewmodel

import android.text.TextUtils
import androidx.lifecycle.*
import com.example.musicpracticejournal.Event
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.practicefragments.PracticeFragment
import com.example.musicpracticejournal.data.source.local.MusicPracticeRepository
import kotlinx.coroutines.launch

class MusicFragmentViewModel(private val repository: MusicPracticeRepository): ViewModel() {

    companion object {
        val INPUT_TYPE = "INPUT_TYPE" to R.string.fragment_practice_time
        val INPUT_NAME = "INPUT_NAME" to R.string.fragment_name
        val INPUT_AUTHOR = "INPUT_AUTHOR" to R.string.fragment_author
        val INPUT_PRACTICE_TIME = "INPUT_PRACTICE_TIME" to R.string.fragment_practice_time
        val INPUT_PRACTICE_DATE = "INPUT_PRACTICE_DATE" to R.string.fragment_practice_date
    }

    sealed class CreateMusicFragmentState {
        class CreateMusicFragmentSaved : CreateMusicFragmentState()
        class CreateMusicFragmentWithInvalidFields(val fields: List<Pair<String, Int>>) :
            CreateMusicFragmentState()
    }

    private val _emptyMessageVisibility = MutableLiveData<Boolean>()
    val emptyMessageVisibility : LiveData<Boolean> = _emptyMessageVisibility

    private val _practiceFragments: LiveData<List<PracticeFragment>> = repository.allPracticeFragments.asLiveData()
    val practiceFragments : LiveData<List<PracticeFragment>> = _practiceFragments

    private val _createMusicFragmentState = MutableLiveData<CreateMusicFragmentState>()
    val createMusicFragmentState: LiveData<CreateMusicFragmentState>
        get() = _createMusicFragmentState

    private val _createPracticeFragmentEvent = MutableLiveData<Event<Unit>>()
    val createPracticeFragmentEvent: LiveData<Event<Unit>> = _createPracticeFragmentEvent

    private val _startPracticeEvent = MutableLiveData<Event<PracticeFragment>>()
    val startPracticeEvent: LiveData<Event<PracticeFragment>> = _startPracticeEvent

    fun insert(practiceFragment: PracticeFragment) = viewModelScope.launch {
        if (validateCreateForm(practiceFragment)) {
            _createMusicFragmentState.value = CreateMusicFragmentState.CreateMusicFragmentSaved()
            practiceFragment.practiceDate.replace(" ", "")
            repository.insert(practiceFragment)
        }
    }

    private fun validateCreateForm(practiceFragment: PracticeFragment):Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (TextUtils.isEmpty(practiceFragment.type)) {
            invalidFields.add(INPUT_TYPE)
        }
        if (TextUtils.isEmpty(practiceFragment.name)) {
            invalidFields.add(INPUT_NAME)
        }
        if (TextUtils.isEmpty(practiceFragment.author)) {
            invalidFields.add(INPUT_AUTHOR)
        }
        if (TextUtils.isEmpty(practiceFragment.practiceTime)) {
            invalidFields.add(INPUT_PRACTICE_TIME)
        }
        if (TextUtils.isEmpty(practiceFragment.practiceDate)) {
            invalidFields.add(INPUT_PRACTICE_DATE)
        }
        if (invalidFields.isNotEmpty()) {
            _createMusicFragmentState.value = CreateMusicFragmentState.CreateMusicFragmentWithInvalidFields(invalidFields)
            return false
        }

        return true
    }


    fun updateEmptyListText(practiceFragments: List<PracticeFragment>?) {
        _emptyMessageVisibility.value = practiceFragments.isNullOrEmpty()
    }

    fun deleteAllMusicFragments() = viewModelScope.launch {
        repository.deleteAllMusicFragments()
    }

    fun createPracticeFragment() {
        _createPracticeFragmentEvent.value = Event(Unit)
    }

    fun startPractice(musicFragment: PracticeFragment) {
        _startPracticeEvent.value = Event(musicFragment)
    }

    fun addMockPracticeFragment() = viewModelScope.launch {
        val practiceFragment = PracticeFragment("Song", "LMB", "Absent Minded", "1", "02/03/2022")
        repository.insert(practiceFragment)
    }
}