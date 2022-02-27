package com.example.musicpracticejournal.injection

import android.app.Application
import androidx.room.Room
import com.example.musicpracticejournal.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, "musicPractice-db").build()

}