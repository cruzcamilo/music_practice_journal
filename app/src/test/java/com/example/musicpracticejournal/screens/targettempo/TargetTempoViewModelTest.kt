package com.example.musicpracticejournal.screens.targettempo

import android.view.View
import androidx.lifecycle.SavedStateHandle
import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.UpdateTargetTempoUseCase
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TargetTempoViewModelTest: BaseViewModelTest() {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val id = 1L
    private val targetTempo = "160"
    @MockK private lateinit var updateTargetTempoUseCaseMock: UpdateTargetTempoUseCase
    @RelaxedMockK private lateinit var resourceManagerMock: ResourceManager
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("entryId" to id))
    private lateinit var SUT: TargetTempoViewModel

    @Before
    fun setUp() {
        SUT = TargetTempoViewModel(updateTargetTempoUseCaseMock, resourceManagerMock, savedStateHandle)
    }

    @Test
    fun `descriptionText is correct when switch is enabled`() {
        val expectedDescription = "Track tempo enabled"
        coEvery { resourceManagerMock.getString(R.string.target_tempo_description_enabled) } returns expectedDescription

        val description = SUT.descriptionText.getOrAwaitValue()

        assertThat(description).isEqualTo(expectedDescription)
    }

    @Test
    fun `descriptionText is correct when switch is not enabled`() {
        val expectedDescription = "Track tempo disabled"
        coEvery { resourceManagerMock.getString(R.string.target_tempo_description_disabled) } returns expectedDescription

        SUT.updateSwitchState(false)

        val description = SUT.descriptionText.getOrAwaitValue()
        assertThat(description).isEqualTo(expectedDescription)
    }

    @Test
    fun `textInputVisibility is visible when switch is enabled (default)`() {
        assertThat(SUT.textInputVisibility.value).isEqualTo(View.VISIBLE)

        SUT.updateSwitchState(true)
        assertThat(SUT.textInputVisibility.getOrAwaitValue()).isEqualTo(View.VISIBLE)
    }

    @Test
    fun `textInputVisibility is gone when switch is not enabled`() {
        SUT.updateSwitchState(false)
        assertThat(SUT.textInputVisibility.getOrAwaitValue()).isEqualTo(View.GONE)
    }

    @Test
    fun `saveButtonEnabled is true when switch is disabled`() {
        SUT.updateSwitchState(false)
        assertThat(SUT.saveButtonEnabled.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun `saveButtonEnabled is false when switch is enabled and tempo is empty`() {
        assertThat(SUT.targetTempo.value).isEmpty()
        assertThat(SUT.saveButtonEnabled.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun `isSaveButtonEnabled is true when switch is enabled and tempo is not empty`() {
        SUT.targetTempo.value = targetTempo
        assertThat(SUT.saveButtonEnabled.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun `updateSwitchState clears target tempo if not checked`() {
        SUT.targetTempo.value = targetTempo
        SUT.updateSwitchState(false)
        assertThat(SUT.targetTempo.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `save uses correct params`()  {
        SUT.targetTempo.value = targetTempo
        val slot = slot<UpdateTargetTempoUseCase.Params>()
        coEvery { updateTargetTempoUseCaseMock(capture(slot)) } just runs

        SUT.save()

        coVerify (exactly = 1) { updateTargetTempoUseCaseMock.invoke(any()) }
        assertThat(SUT.entryId).isEqualTo(slot.captured.id)
        assertThat(SUT.targetTempo.value?.toInt()).isEqualTo(slot.captured.targetTempo)
    }

    @Test
    fun `save updates event value`() {
        SUT.targetTempo.value = targetTempo
        coEvery { updateTargetTempoUseCaseMock(any()) } just runs

        SUT.save()

        assertThat(SUT.event.value).isEqualTo(TargetTempoViewModel.Event.ToPracticeScreen)
    }

}