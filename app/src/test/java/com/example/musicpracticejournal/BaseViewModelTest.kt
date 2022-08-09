package com.example.musicpracticejournal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
}