package com.example.musicpracticejournal.screens.currenttempo

import androidx.lifecycle.SavedStateHandle
import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.domain.usecase.UpdateCurrentTempoUseCase
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrentTempoViewModelTest : BaseViewModelTest() {

    private val id = 1L
    private val currentTempo = "60"
    private val updateCurrentTempoUseCaseMock: UpdateCurrentTempoUseCase = mockk(relaxed = true)
    private val savedStateHandleMock: SavedStateHandle = SavedStateHandle(mapOf("entryId" to id))
    private lateinit var SUT: CurrentTempoViewModel

    @Before
    fun setUp() {
        SUT = CurrentTempoViewModel(updateCurrentTempoUseCaseMock, savedStateHandleMock)
    }

    @Test
    fun `saveButtonEnabled is false if current tempo is empty`() {
        assertThat(SUT.saveButtonEnabled.getOrAwaitValue()).isFalse()
    }

    @Test
    fun `saveButtonEnabled is true if current tempo is not empty`() {
        SUT.currentTempo.value = currentTempo
        assertThat(SUT.saveButtonEnabled.getOrAwaitValue()).isTrue()
    }

    @Test
    fun `save uses correct params to update current tempo`() {
        SUT.currentTempo.value = currentTempo
        val updateCurrentTempoSlot = slot<UpdateCurrentTempoUseCase.Params>()
        coEvery { updateCurrentTempoUseCaseMock.invoke(capture(updateCurrentTempoSlot)) } just runs

        SUT.save()

        coVerify (exactly = 1) { updateCurrentTempoUseCaseMock.invoke(any()) }

        assertThat(currentTempo.toInt()).isEqualTo(updateCurrentTempoSlot.captured.currentTempo)
        assertThat(id).isEqualTo(updateCurrentTempoSlot.captured.id)
    }

    @Test
    fun `save updates event value to ToToPracticeScreen`() {
        SUT.currentTempo.value = currentTempo
        coEvery {
            updateCurrentTempoUseCaseMock.invoke(
                UpdateCurrentTempoUseCase.Params(
                    currentTempo.toInt(),
                    id
                )
            )
        } just runs

        SUT.save()

        assertThat(SUT.event.value).isEqualTo(CurrentTempoViewModel.Event.ToPracticeScreen)
    }
}