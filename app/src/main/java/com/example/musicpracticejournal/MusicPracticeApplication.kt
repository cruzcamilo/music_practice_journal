package com.example.musicpracticejournal

import android.app.Application
import com.example.musicpracticejournal.injection.app.AppModule
import com.example.musicpracticejournal.injection.app.DaggerAppComponent

class MusicPracticeApplication : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}