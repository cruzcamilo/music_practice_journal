package com.example.musicpracticejournal.injection.app

import com.example.musicpracticejournal.screens.create.CreateFragment
import com.example.musicpracticejournal.screens.entertime.EnterTimeSheet
import com.example.musicpracticejournal.screens.home.HomeFragment
import com.example.musicpracticejournal.screens.originaltempo.OriginalTempoSheet
import com.example.musicpracticejournal.screens.practice.PracticeFragment
import com.example.musicpracticejournal.screens.review.ReviewFragment
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(fragment: CreateFragment)
    fun inject(fragment: EnterTimeSheet)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: OriginalTempoSheet)
    fun inject(fragment: PracticeFragment)
    fun inject(fragment: ReviewFragment)

}