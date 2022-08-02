package com.example.musicpracticejournal.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeFragmentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePracticeFragment(musicFragment: MusicFragment): Long

    @Update
    suspend fun updatePracticeFragment(musicFragment: MusicFragment): Int

    @Transaction
    @Query("UPDATE musical_fragment_table set updated =:updateDate WHERE id = :fragmentId")
    suspend fun updatePracticeFragmentDate(updateDate: String, fragmentId: Long): Int

    @Transaction
    @Query("UPDATE musical_fragment_table set targetTempo =:originalTempo WHERE id = :fragmentId")
    suspend fun updateOriginalTempo(originalTempo: Int, fragmentId: Long): Int

    @Transaction
    @Query("UPDATE musical_fragment_table set currentTempo =:currentTempo WHERE id = :fragmentId")
    suspend fun updateCurrentTempo(currentTempo: Int, fragmentId: Long): Int

    @Transaction
    @Query("SELECT * FROM musical_fragment_table ORDER BY id ASC")
    fun getAll(): Flow<List<MusicFragment>>

    @Transaction
    @Query("SELECT * FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun getMusicFragmentById(fragmentId: Long): MusicFragment?

    @Query("DELETE FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun deleteMusicFragmentById(fragmentId: String): Int

    @Query("DELETE FROM musical_fragment_table")
    suspend fun deleteAlLMusicFragments()

}