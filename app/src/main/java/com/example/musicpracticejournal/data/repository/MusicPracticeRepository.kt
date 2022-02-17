package com.example.musicpracticejournal.data.repository

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.db.entity.PracticeFragment
import com.example.musicpracticejournal.data.db.dao.PracticeFragmentDao
import com.example.musicpracticejournal.data.db.entity.Review
import com.example.musicpracticejournal.data.db.dao.ReviewDao
import kotlinx.coroutines.flow.Flow

class MusicPracticeRepository(
    private val practiceFragmentDao: PracticeFragmentDao,
    private val reviewDao: ReviewDao
) {

    val allPracticeFragments: Flow<List<PracticeFragment>> = practiceFragmentDao.getAllMusicFragments()
    val allReviews : Flow<List<Review>> = reviewDao.getAllReviews()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun savePracticeFragment(word: PracticeFragment) {
        practiceFragmentDao.savePracticeFragment(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveReview(review: Review) {
        reviewDao.saveReview(review)
    }

    suspend fun deleteAllMusicFragments() {
        practiceFragmentDao.deleteAlLMusicFragments()
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun updatePracticeDate(date: String, fragmentId: Long) {
        practiceFragmentDao.updatePracticeFragmentDate(date, fragmentId)
    }
}