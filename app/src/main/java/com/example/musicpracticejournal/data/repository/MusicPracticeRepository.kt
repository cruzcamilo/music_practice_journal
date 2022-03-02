package com.example.musicpracticejournal.data.repository

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.PracticeFragment
import com.example.musicpracticejournal.data.db.entity.Review
import com.example.musicpracticejournal.practicefragments.PracticeStateEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicPracticeRepository @Inject constructor(
    private val database: AppDatabase
) {

    val allPracticeFragments: Flow<List<PracticeFragment>> = database.practiceFragmentDao().getAllMusicFragments()
    val allReviews : Flow<List<Review>> = database.reviewDao().getAllReviews()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun savePracticeFragment(word: PracticeFragment) {
        database.practiceFragmentDao().savePracticeFragment(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveMock() {
        val practiceFragment = PracticeFragment("Song", "Drowning", "Post solo", "1", PracticeStateEnum.ACTIVE.name, 180, 150)
        database.practiceFragmentDao().savePracticeFragment(practiceFragment)
    }

    @WorkerThread
    suspend fun getPracticeFragment(id: Long): PracticeFragment? {
        return database.practiceFragmentDao().getMusicFragmentById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveReview(review: Review) {
        database.reviewDao().saveReview(review)
    }

    suspend fun deleteAllMusicFragments() {
        database.practiceFragmentDao().deleteAlLMusicFragments()
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun updatePracticeDate(date: String, fragmentId: Long) {
        database.practiceFragmentDao().updatePracticeFragmentDate(date, fragmentId)
    }
}