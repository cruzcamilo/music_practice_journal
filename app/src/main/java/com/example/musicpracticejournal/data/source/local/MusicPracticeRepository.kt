package com.example.musicpracticejournal.data.source.local

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.practicefragments.PracticeFragment
import kotlinx.coroutines.flow.Flow

class MusicPracticeRepository(private val musicFragmentDao: MusicFragmentDao) {

    val allPracticeFragments: Flow<List<PracticeFragment>> = musicFragmentDao.getAllMusicFragments()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: PracticeFragment) {
        musicFragmentDao.insertMusicFragment(word)
    }

    suspend fun deleteAllMusicFragments() {
        musicFragmentDao.deleteAlLMusicFragments()
    }
}