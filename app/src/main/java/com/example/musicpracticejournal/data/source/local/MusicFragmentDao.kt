package com.example.musicpracticejournal.data.source.local

import androidx.room.*
import com.example.musicpracticejournal.data.MusicFragment
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicFragmentDao {

    @Query("SELECT * FROM musical_fragment_table ORDER BY id ASC")
    fun getAllMusicFragments(): Flow<List<MusicFragment>>

    @Query("SELECT * FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun getMusicFragmentById(fragmentId: Long): MusicFragment?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusicFragment(musicFragment: MusicFragment): Long

    @Update
    suspend fun updateMusicFragment(musicFragment: MusicFragment): Int

    @Query("DELETE FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun deleteMusicFragmentById(fragmentId: String): Int

    @Query("DELETE FROM musical_fragment_table")
    suspend fun deleteAlLMusicFragments()

}