package com.example.musicpracticejournal.di

import android.content.Context
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.entity.Timer
import com.example.musicpracticejournal.domain.entity.TimerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager = ResourceManager(context)

    @Provides
    fun provideTimerImpl(timerscope: CoroutineScope): Timer = TimerImpl(timerscope)

}