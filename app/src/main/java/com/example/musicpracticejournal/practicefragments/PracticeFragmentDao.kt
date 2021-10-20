package com.example.musicpracticejournal.practicefragments

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeFragmentDao {

    @Query("SELECT * FROM musical_fragment_table ORDER BY id ASC")
    fun getAllMusicFragments(): Flow<List<PracticeFragment>>

    @Query("SELECT * FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun getMusicFragmentById(fragmentId: Long): PracticeFragment?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePracticeFragment(practiceFragment: PracticeFragment): Long

    @Update
    suspend fun updateMusicFragment(practiceFragment: PracticeFragment): Int

    @Query("DELETE FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun deleteMusicFragmentById(fragmentId: String): Int

    @Query("DELETE FROM musical_fragment_table")
    suspend fun deleteAlLMusicFragments()

}