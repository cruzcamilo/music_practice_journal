package com.example.musicpracticejournal.injection.app

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
    fun application() = application

    @Provides
    fun context(): Context = application.applicationContext

    @Provides
    @AppScope
    fun roomDatabase(app: Application): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, "musicPractice-db").build()

    @Provides
    @AppScope
    fun resourceManager(context: Context): ResourceManager = ResourceManager(context)
}