package com.example.musicpracticejournal

import android.app.Application
import com.example.musicpracticejournal.di.app.AppComponent
import com.example.musicpracticejournal.di.app.AppModule
import com.example.musicpracticejournal.di.app.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}