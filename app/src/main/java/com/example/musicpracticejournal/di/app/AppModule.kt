package com.example.musicpracticejournal.di.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.domain.ResourceManager
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {
    
    @Provides
    @AppScope
    fun application() = application

    @Provides
    @AppScope
    fun context(): Context = application.applicationContext

    @Provides
    @AppScope
    fun roomDatabase(app: Application): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, "musicPractice-db").build()

    @Provides
    @AppScope
    fun resourceManager(context: Context): ResourceManager = ResourceManager(context)
}