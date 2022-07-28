package com.example.musicpracticejournal.di.presentation

import androidx.lifecycle.ViewModel
import com.example.musicpracticejournal.di.ViewModelKey
import com.example.musicpracticejournal.screens.create.CreateViewModel
import com.example.musicpracticejournal.screens.entertime.EnterTimeViewModel
import com.example.musicpracticejournal.screens.home.HomeViewModel
import com.example.musicpracticejournal.screens.originaltempo.OriginalTempoViewModel
import com.example.musicpracticejournal.screens.practice.PracticeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateViewModel::class)
    abstract fun createViewModel(createViewModel: CreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnterTimeViewModel::class)
    abstract fun enterTimeViewModel(enterTimeViewModel: EnterTimeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PracticeViewModel::class)
    abstract fun practiceViewModel(practiceViewModel: PracticeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OriginalTempoViewModel::class)
    abstract fun originalTempoViewModel(originalTempoViewModel: OriginalTempoViewModel): ViewModel

}