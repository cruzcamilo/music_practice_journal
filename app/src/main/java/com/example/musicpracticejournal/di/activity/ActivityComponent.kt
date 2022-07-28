package com.example.musicpracticejournal.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.musicpracticejournal.di.presentation.PresentationComponent
import com.example.musicpracticejournal.di.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }

}