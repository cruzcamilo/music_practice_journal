package com.example.musicpracticejournal.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.musicpracticejournal.data.db.entity.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEntry(entry: Entry): Long

    @Update
    suspend fun updateEntry(entry: Entry): Int

    @Transaction
    @Query("UPDATE Entry set updated =:updateDate WHERE id = :entryId")
    suspend fun updateEntryDate(updateDate: String, entryId: Long): Int

    @Transaction
    @Query("UPDATE Entry set targetTempo =:targetTempo WHERE id = :entryId")
    suspend fun updateTargetTempo(targetTempo: Int, entryId: Long): Int

    @Transaction
    @Query("UPDATE Entry set currentTempo =:currentTempo WHERE id = :entryId")
    suspend fun updateCurrentTempo(currentTempo: Int, entryId: Long): Int

    @Transaction
    @Query("SELECT * FROM Entry ORDER BY id ASC")
    fun getAllEntries(): Flow<List<Entry>>

    @Transaction
    @Query("SELECT * FROM Entry WHERE id = :entryId")
    suspend fun getEntryById(entryId: Long): Entry

    @Query("DELETE FROM Entry WHERE id = :entryId")
    suspend fun deleteEntryById(entryId: String): Int

    @Query("DELETE FROM Entry")
    suspend fun deleteEntries()

}