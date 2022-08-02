package com.example.musicpracticejournal.data.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Entry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicPracticeRepository @Inject constructor(
    private val database: AppDatabase
) {

    fun getMusicFragments(): Flow<List<Entry>> {
        return database.practiceFragmentDao().getAll()
    }

    @WorkerThread
    suspend fun getPracticeFragment(id: Long): Entry? {
        return database.practiceFragmentDao().getPracticeFragmentById(id)
    }

    @WorkerThread
    suspend fun savePracticeFragment(entry: Entry) {
        database.practiceFragmentDao().savePracticeFragment(entry)
    }

    @WorkerThread
    suspend fun updatePracticeFragment(entry: Entry) {
        database.practiceFragmentDao().updatePracticeFragment(entry)
    }

    suspend fun updatePracticeDate(date: String, entryId: Long) {
        database.practiceFragmentDao().updatePracticeFragmentDate(date, entryId)
    }

    suspend fun updateOriginalTempo(originalTempo: Int, entryId: Long) {
        database.practiceFragmentDao().updateOriginalTempo(originalTempo, entryId)
    }

    suspend fun updateCurrentTempo(currentTempo: Int, entryId: Long) {
        database.practiceFragmentDao().updateCurrentTempo(currentTempo, entryId)
    }

    suspend fun deleteAllMusicFragments() {
        database.practiceFragmentDao().deleteAlLMusicFragments()
    }

    @WorkerThread
    suspend fun saveMock() {
        val entry = Entry(
            "Song",
            "Drowning",
            "Post solo",
            "1",
            null,
            null
        )
        database.practiceFragmentDao().savePracticeFragment(entry)
    }
}