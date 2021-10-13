package com.example.musicpracticejournal.data.source.local

import androidx.room.*
import com.example.musicpracticejournal.practicefragments.PracticeFragment
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicFragmentDao {

    @Query("SELECT * FROM musical_fragment_table ORDER BY id ASC")
    fun getAllMusicFragments(): Flow<List<PracticeFragment>>

    @Query("SELECT * FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun getMusicFragmentById(fragmentId: Long): PracticeFragment?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusicFragment(practiceFragment: PracticeFragment): Long

    @Update
    suspend fun updateMusicFragment(practiceFragment: PracticeFragment): Int

    @Query("DELETE FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun deleteMusicFragmentById(fragmentId: String): Int

    @Query("DELETE FROM musical_fragment_table")
    suspend fun deleteAlLMusicFragments()

}