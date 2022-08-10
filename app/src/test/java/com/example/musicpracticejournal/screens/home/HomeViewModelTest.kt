package com.example.musicpracticejournal.screens.home

import android.view.View
import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.domain.usecase.DeleteAllEntriesUseCase
import com.example.musicpracticejournal.domain.usecase.RetrieveEntriesUseCase
import com.example.musicpracticejournal.domain.usecase.SaveTestEntryUseCase
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class HomeViewModelTest: BaseViewModelTest() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK private lateinit var saveTestEntryUseCaseMock: SaveTestEntryUseCase
    @MockK private lateinit var deleteAllEntriesUseCaseMock: DeleteAllEntriesUseCase
    @MockK private lateinit var retrieveEntriesUseCaseMock: RetrieveEntriesUseCase
    private val entryList = listOf<Entry>(mockk(relaxed = true), mockk(relaxed = true))
    private lateinit var SUT: HomeViewModel

    @Test
    fun `retrieveEntries is invoked on ViewModel init`() {
        retrieveEmptyEntryList()

        SUT = HomeViewModel(saveTestEntryUseCaseMock, deleteAllEntriesUseCaseMock, retrieveEntriesUseCaseMock)

        coVerify (exactly = 1) { retrieveEntriesUseCaseMock.invoke(Unit) }
    }

    @Test
    fun `emptyImageVisibility is visible if entries is empty`() {
        retrieveEmptyEntryList()

        SUT = HomeViewModel(saveTestEntryUseCaseMock, deleteAllEntriesUseCaseMock, retrieveEntriesUseCaseMock)

        assertThat(SUT.entries.value).isEmpty()
        assertThat(SUT.emptyImageVisibility.getOrAwaitValue()).isEqualTo(View.VISIBLE)
    }

    @Test
    fun `emptyImageVisibility is gone if entries has data`() {
        retrieveEntryList()

        SUT = HomeViewModel(saveTestEntryUseCaseMock, deleteAllEntriesUseCaseMock, retrieveEntriesUseCaseMock)

        assertThat(SUT.entries.value).isEqualTo(entryList)
        assertThat(SUT.emptyImageVisibility.getOrAwaitValue()).isEqualTo(View.GONE)
    }

    @Test
    fun `goToCreateScreen updates event value`() {
        retrieveEmptyEntryList()

        SUT = HomeViewModel(saveTestEntryUseCaseMock, deleteAllEntriesUseCaseMock, retrieveEntriesUseCaseMock)
        SUT.goToCreateScreen()

        assertThat(SUT.event.value).isEqualTo(HomeViewModel.Event.CreateScreen)
    }


    private fun retrieveEmptyEntryList() {
        coEvery { retrieveEntriesUseCaseMock.invoke(Unit) } returns flow {
            emit(emptyList())
        }
    }

    private fun retrieveEntryList() {
        coEvery { retrieveEntriesUseCaseMock.invoke(Unit) } returns flow {
            emit(entryList)
        }
    }
}