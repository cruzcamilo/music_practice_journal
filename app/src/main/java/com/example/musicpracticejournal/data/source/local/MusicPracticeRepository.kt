package com.example.musicpracticejournal.data.source.local

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.practicefragments.PracticeFragment
import com.example.musicpracticejournal.practicefragments.PracticeFragmentDao
import com.example.musicpracticejournal.reviews.Review
import com.example.musicpracticejournal.reviews.ReviewDao
import kotlinx.coroutines.flow.Flow

class MusicPracticeRepository(private val practiceFragmentDao: PracticeFragmentDao,
                              private val reviewDao: ReviewDao) {

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
}