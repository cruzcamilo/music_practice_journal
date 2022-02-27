package com.example.musicpracticejournal.screens.create

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.PracticeFragment
import com.example.musicpracticejournal.data.db.entity.Review
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: MusicPracticeRepository
    ) : ViewModel() {

    companion object {
        val INPUT_TYPE = "INPUT_TYPE" to R.string.fragment_practice_time
        val INPUT_NAME = "INPUT_NAME" to R.string.fragment_name
        val INPUT_AUTHOR = "INPUT_AUTHOR" to R.string.fragment_author
        val INPUT_PRACTICE_TIME = "INPUT_PRACTICE_TIME" to R.string.fragment_practice_time
    }

    sealed class CreateMusicFragmentState {
        class CreateMusicFragmentSaved : CreateMusicFragmentState()
        class CreateMusicFragmentWithInvalidFields(val fields: List<Pair<String, Int>>) :
            CreateMusicFragmentState()
    }

    private val _practiceFragments: LiveData<List<PracticeFragment>> = repository.allPracticeFragments.asLiveData()
    val practiceFragments : LiveData<List<PracticeFragment>> = _practiceFragments

    private val _createMusicFragmentState = MutableLiveData<CreateMusicFragmentState>()
    val createMusicFragmentState: LiveData<CreateMusicFragmentState>
        get() = _createMusicFragmentState

    private val _reviews: LiveData<List<Review>> = repository.allReviews.asLiveData()
    val reviews : LiveData<List<Review>> = _reviews

    fun insert(practiceFragment: PracticeFragment) = viewModelScope.launch {
        if (validateCreateForm(practiceFragment)) {
            _createMusicFragmentState.value = CreateMusicFragmentState.CreateMusicFragmentSaved()
            repository.savePracticeFragment(practiceFragment)
        }
    }

    private fun validateCreateForm(practiceFragment: PracticeFragment):Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        with(practiceFragment) {
            if (TextUtils.isEmpty(type)) invalidFields.add(INPUT_TYPE)
            if (TextUtils.isEmpty(name)) invalidFields.add(INPUT_NAME)
            if (TextUtils.isEmpty(author)) invalidFields.add(INPUT_AUTHOR)
            if (TextUtils.isEmpty(practiceTime)) invalidFields.add(INPUT_PRACTICE_TIME)
        }
        if (invalidFields.isNotEmpty()) {
            _createMusicFragmentState.value =
                CreateMusicFragmentState.CreateMusicFragmentWithInvalidFields(invalidFields)
            return false
        }

        return true
    }
}