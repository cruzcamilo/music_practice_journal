package com.example.musicpracticejournal.data.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Entry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntryRepository @Inject constructor(
    private val database: AppDatabase
) {

    fun getMusicFragments(): Flow<List<Entry>> {
        return database.entryDao().getAllEntries()
    }

    @WorkerThread
    suspend fun getEntry(id: Long): Entry? {
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

    suspend fun updateOriginalTempo(originalTempo: Int, entryId: Long) {
        database.entryDao().updateOriginalTempo(originalTempo, entryId)
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