package com.example.musicpracticejournal.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicpracticejournal.MusicFragment
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicFragmentDao {

    @Query("SELECT * FROM musical_fragment_table ORDER BY name ASC")
    fun getCurrentWeekPractice(): Flow<List<MusicFragment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(musicFragment: MusicFragment)

}
