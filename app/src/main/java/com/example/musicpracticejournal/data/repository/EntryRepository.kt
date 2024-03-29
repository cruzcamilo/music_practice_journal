package com.example.musicpracticejournal.data.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.mapper.toDomain
import com.example.musicpracticejournal.domain.entity.EntryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntryRepository @Inject constructor(private val database: AppDatabase) {

    fun getMusicFragments(): Flow<List<EntryItem>> {
        return database.entryDao().getAllEntries().toDomain()
    }

    @WorkerThread
    suspend fun getEntry(id: Long): Entry {
        return database.entryDao().getEntryById(id)
    }

    @WorkerThread
    suspend fun saveEntry(entry: Entry) {
        database.entryDao().saveEntry(entry)
    }

    @WorkerThread
    suspend fun updateEntry(entry: Entry) {
        database.entryDao().updateEntry(entry)
    }

    suspend fun updatePracticeDate(date: String, entryId: Long) {
        database.entryDao().updateEntryDate(date, entryId)
    }

    suspend fun updateTargetTempo(targetTempo: Int, entryId: Long) {
        database.entryDao().updateTargetTempo(targetTempo, entryId)
    }

    suspend fun updateCurrentTempo(currentTempo: Int, entryId: Long) {
        database.entryDao().updateCurrentTempo(currentTempo, entryId)
    }

    suspend fun deleteAllEntries() {
        database.entryDao().deleteEntries()
    }

    @WorkerThread
    suspend fun saveMock() {
        val entry = Entry(
            "Song",
            "Drowning",
            "Post solo",
            "1",
            true,
            null
        )
        database.entryDao().saveEntry(entry)
    }
}