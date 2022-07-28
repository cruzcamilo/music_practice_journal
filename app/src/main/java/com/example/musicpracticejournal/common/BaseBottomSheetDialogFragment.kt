package com.example.musicpracticejournal.common


import com.example.musicpracticejournal.di.presentation.PresentationModule
import com.example.musicpracticejournal.screens.activities.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this, this.arguments)
        )
    }

    protected val injector get() = presentationComponent
}