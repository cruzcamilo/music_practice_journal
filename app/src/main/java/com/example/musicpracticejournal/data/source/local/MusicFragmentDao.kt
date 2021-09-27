package com.example.musicpracticejournal.data.source.local

import androidx.room.*
import com.example.musicpracticejournal.data.MusicFragment
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicFragmentDao {

    @Query("SELECT * FROM musical_fragment_table ORDER BY name ASC")
    fun getAllMusicFragments(): Flow<List<MusicFragment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusicFragment(musicFragment: MusicFragment)

    @Update
    suspend fun updateMusicFragment(musicFragment: MusicFragment): Int

    @Query("DELETE FROM musical_fragment_table WHERE id = :fragmentId")
    suspend fun deleteMusicFragmentById(fragmentId: String): Int

    @Query("DELETE FROM musical_fragment_table")
    suspend fun deleteAlLMusicFragments()

}