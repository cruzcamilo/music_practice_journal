package com.example.musicpracticejournal.data.source.local

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.data.MusicFragment
import kotlinx.coroutines.flow.Flow

class MusicPracticeRepository(private val musicFragmentDao: MusicFragmentDao) {

    val allMusicFragments: Flow<List<MusicFragment>> = musicFragmentDao.getAllMusicFragments()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: MusicFragment) {
        musicFragmentDao.insertMusicFragment(word)
    }

    suspend fun deleteAllMusicFragments() {
        musicFragmentDao.deleteAlLMusicFragments()
    }
}