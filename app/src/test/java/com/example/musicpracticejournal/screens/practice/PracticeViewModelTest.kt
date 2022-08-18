package com.example.musicpracticejournal.screens.practice

import android.graphics.drawable.Drawable
import androidx.lifecycle.SavedStateHandle
import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.TimerUseCase
import com.example.musicpracticejournal.util.timeStringToSeconds
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PracticeViewModelTest: BaseViewModelTest() {

    private val entryId = 1L
    private val entryRepositoryMock: EntryRepository = mockk(relaxed = true)
    private val timerUseCaseMock: TimerUseCase = mockk(relaxed = true)
    private val resourceManagerMock: ResourceManager = mockk(relaxed = true)
    private val savedStateHandleMock: SavedStateHandle = SavedStateHandle(mapOf("entryId" to entryId))
    private lateinit var SUT: PracticeViewModel

    private val playIconMock = mockk<Drawable>(relaxed = true)

    @Before
    fun setup() {

    }

    @Test
    fun `btnActionImage is play icon when timerState is stopped`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.STOPPED)

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.STOPPED)
        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is play icon when timerState is paused`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.PAUSED)

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.PAUSED)
        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is play icon when timerState is finished`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.FINISHED)

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.FINISHED)
        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is pause icon when timerState is active`() {
        val pauseIconMock = mockk<Drawable>(relaxed = true)
        every { resourceManagerMock.getDrawable(R.drawable.ic_pause_circle) } returns pauseIconMock
        setupInitialTimerState(TimerStateEnum.ACTIVE)

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.ACTIVE)
        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(pauseIconMock)
    }

    @Test
    fun `operateTimer updates event timer state is stopped and target tempo is missing`() {
        setupInitialTimerState(TimerStateEnum.STOPPED)
        coEvery { entryRepositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns true
            every { targetTempo } returns null
        }
        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.STOPPED)
        SUT.onStart()
        SUT.operateTimer()

        assertThat(SUT.event.value).isInstanceOf(PracticeViewModel.Event.TargetTempo::class.java)
        assertThat((SUT.event.value as PracticeViewModel.Event.TargetTempo).entryId).isEqualTo(entryId)
    }

    @Test
    fun `operateTimer starts timer when timerState is paused`() {
        setupInitialTimerState(TimerStateEnum.PAUSED)
        val defaultTimerSeconds = DEFAULT_TIMER_VALUE.timeStringToSeconds()

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)
        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.PAUSED)

        SUT.onStart()
        SUT.operateTimer()
        val timerSeconds = slot<Long>()

        verify (exactly = 1) { timerUseCaseMock.start(capture(timerSeconds)) }
        assertThat(defaultTimerSeconds).isEqualTo(timerSeconds.captured)
    }

    @Test
    fun `operateTimer starts timer when timerState is finished`() {
        setupInitialTimerState(TimerStateEnum.FINISHED)
        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.FINISHED)

        SUT.onStart()
        SUT.operateTimer()

        verify (exactly = 1) { timerUseCaseMock.start(any()) }
    }

    @Test
    fun `operateTimer pauses timer if timerState is active`() {
        setupInitialTimerState(TimerStateEnum.ACTIVE)
        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.ACTIVE)

        SUT.onStart()
        SUT.operateTimer()

        verify (exactly = 1) { timerUseCaseMock.pause() }
    }

    @Test
    fun `startTimer uses correct default timer seconds value`() {
        setupInitialTimerState(TimerStateEnum.PAUSED)
        val defaultTimerSeconds = DEFAULT_TIMER_VALUE.timeStringToSeconds()

        SUT = PracticeViewModel(entryRepositoryMock, timerUseCaseMock, resourceManagerMock, savedStateHandleMock)
        assertThat(SUT.timerState.getOrAwaitValue()).isEqualTo(TimerStateEnum.PAUSED)

        SUT.onStart()
        SUT.operateTimer()
        val timerSeconds = slot<Long>()

        verify (exactly = 1) { timerUseCaseMock.start(capture(timerSeconds)) }
        assertThat(defaultTimerSeconds).isEqualTo(timerSeconds.captured)
    }

    private fun setupInitialTimerState(stateEnum: TimerStateEnum) {
        every { timerUseCaseMock.timerState } returns flow {
            emit(stateEnum)
        }
    }
}