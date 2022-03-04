package com.example.musicpracticejournal.data.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepository@Inject constructor(
    private val database: AppDatabase
)  {
    val allReviews : Flow<List<Review>> = database.reviewDao().getAllReviews()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveReview(review: Review) {
        database.reviewDao().saveReview(review)
    }
}