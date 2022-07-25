package com.example.musicpracticejournal.injection.coroutines

import com.example.musicpracticejournal.injection.app.AppScope
import dagger.Component
import kotlinx.coroutines.CoroutineScope

@AppScope
@Component(modules = [CoroutinesModule::class])
interface CoroutinesComponent {

    fun coroutineScope(): CoroutineScope

}