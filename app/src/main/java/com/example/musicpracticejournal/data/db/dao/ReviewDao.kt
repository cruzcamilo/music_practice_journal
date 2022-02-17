package com.example.musicpracticejournal.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicpracticejournal.data.db.entity.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review")
    fun getAllReviews(): Flow<List<Review>>

    @Query("SELECT * FROM review WHERE practiceFragmentId = :fragmentId")
    suspend fun getMusicFragmentById(fragmentId: Long): Review?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveReview(review: Review): Long

    @Query("DELETE FROM review")
    suspend fun deleteAlLMusicFragments()

}