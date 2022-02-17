package com.example.musicpracticejournal

import android.app.Application
import com.example.musicpracticejournal.data.MusicPracticeDb
import com.example.musicpracticejournal.data.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.data.repository.MusicPracticeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MusicPracticeApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MusicPracticeDb.getDatabase(this, applicationScope) }
    val repository by lazy { MusicPracticeRepository(database.musicFragmentDao(), database.reviewDao()) }
    val timerUseCase by lazy { TimerUseCase(applicationScope) }
}