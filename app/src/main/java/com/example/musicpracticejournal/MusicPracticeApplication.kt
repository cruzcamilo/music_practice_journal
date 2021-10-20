package com.example.musicpracticejournal

import android.app.Application
import com.example.musicpracticejournal.data.source.local.MusicPracticeDb
import com.example.musicpracticejournal.data.source.local.MusicPracticeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MusicPracticeApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MusicPracticeDb.getDatabase(this, applicationScope) }
    val repository by lazy { MusicPracticeRepository(database.musicFragmentDao(), database.reviewDao()) }
    val timerUseCase by lazy { TimerUseCase(applicationScope) }
}