package com.example.musicpracticejournal.repository

import androidx.annotation.WorkerThread
import com.example.musicpracticejournal.MusicFragment
import com.example.musicpracticejournal.repository.room.MusicFragmentDao
import kotlinx.coroutines.flow.Flow

class MusicPracticeRepository(private val musicFragmentDao: MusicFragmentDao) {

    val currentWeekFragments: Flow<List<MusicFragment>> = musicFragmentDao.getCurrentWeekPractice()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: MusicFragment) {
        musicFragmentDao.insert(word)
    }
}