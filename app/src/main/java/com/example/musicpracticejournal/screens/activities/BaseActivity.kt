package com.example.musicpracticejournal.screens.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicpracticejournal.MyApplication
import com.example.musicpracticejournal.di.presentation.PresentationModule

open class BaseActivity: AppCompatActivity() {

    private val appComponent get() = (application as MyApplication).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponentBuilder()
            .activity(this)
            .build()
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule(this, args))
    }

    private val args = Bundle()

    protected val injector get() = presentationComponent
}