package com.example.musicpracticejournal.di.app

import com.example.musicpracticejournal.di.activity.ActivityComponent
import com.example.musicpracticejournal.di.presentation.CoroutinesModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, CoroutinesModule::class])
interface AppComponent {
    fun newActivityComponentBuilder(): ActivityComponent.Builder
}