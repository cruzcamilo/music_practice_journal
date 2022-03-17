package com.example.musicpracticejournal.data.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.practicefragments.PracticeStateEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicPracticeRepository @Inject constructor(
    private val database: AppDatabase
) {

    fun getMusicFragments(): Flow<List<MusicFragment>> {
        return database.practiceFragmentDao().getAll()
    }

    @WorkerThread
    suspend fun getPracticeFragment(id: Long): MusicFragment? {
        return database.practiceFragmentDao().getMusicFragmentById(id)
    }

    @WorkerThread
    suspend fun savePracticeFragment(musicFragment: MusicFragment) {
        database.practiceFragmentDao().savePracticeFragment(musicFragment)
    }

    suspend fun updatePracticeDate(date: String, fragmentId: Long) {
        database.practiceFragmentDao().updatePracticeFragmentDate(date, fragmentId)
    }

    suspend fun deleteAllMusicFragments() {
        database.practiceFragmentDao().deleteAlLMusicFragments()
    }

    @WorkerThread
    suspend fun saveMock() {
        val practiceFragment = MusicFragment(
            "Song",
            "Drowning",
            "Post solo",
            "1",
            PracticeStateEnum.ACTIVE.name,
            180,
            150
        )
        database.practiceFragmentDao().savePracticeFragment(practiceFragment)
    }
}