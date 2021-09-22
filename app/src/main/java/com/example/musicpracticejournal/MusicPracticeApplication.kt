package com.example.musicpracticejournal

import android.app.Application
import com.example.musicpracticejournal.repository.MusicPracticeRepository
import com.example.musicpracticejournal.repository.room.MusicPracticeDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MusicPracticeApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MusicPracticeDb.getDatabase(this, applicationScope) }
    val repository by lazy { MusicPracticeRepository(database.musicFragmentDao()) }
}