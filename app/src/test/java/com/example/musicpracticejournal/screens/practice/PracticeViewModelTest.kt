package com.example.musicpracticejournal.screens.practice

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.common.Constants.DEFAULT_TIMER_VALUE
import com.example.musicpracticejournal.data.TimerStateEnum
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.repository.EntryRepository
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.entity.Timer
import com.example.musicpracticejournal.practicefragments.EntryStateEnum
import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.timeStringToSeconds
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

@ExperimentalCoroutinesApi
class PracticeViewModelTest: BaseViewModelTest() {

    private val entryId = 1L
    private val repositoryMock: EntryRepository = mockk(relaxed = true)
    private val timerMock: Timer = mockk(relaxed = true)
    private val resourceManagerMock: ResourceManager = mockk(relaxed = true)
    private val savedStateHandleMock: SavedStateHandle = SavedStateHandle(mapOf("entryId" to entryId))
    private lateinit var SUT: PracticeViewModel

    private val playIconMock = mockk<Drawable>(relaxed = true)

    @Before
    fun setUp() {
        SUT = spyk(PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock))
    }

    @Test
    fun `btnActionImage is play icon when timerState is stopped`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.STOPPED)

        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is play icon when timerState is paused`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.PAUSED)

        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is play icon when timerState is finished`() {
        every { resourceManagerMock.getDrawable(R.drawable.ic_play_circle) } returns playIconMock
        setupInitialTimerState(TimerStateEnum.FINISHED)

        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(playIconMock)
    }

    @Test
    fun `btnActionImage is pause icon when timerState is active`() {
        val pauseIconMock = mockk<Drawable>(relaxed = true)
        every { resourceManagerMock.getDrawable(R.drawable.ic_pause_circle) } returns pauseIconMock
        setupInitialTimerState(TimerStateEnum.ACTIVE)

        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.btnActionImage.getOrAwaitValue()).isEqualTo(pauseIconMock)
    }

    @Test
    fun `isPracticeComplete is true if timerState is finished`() {
        setupInitialTimerState(TimerStateEnum.FINISHED)

        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)

        assertThat(SUT.isPracticeComplete.getOrAwaitValue()).isTrue()
    }

    @Test
    fun `isPracticeComplete is false if timerState is not finished`() {
        setupInitialTimerState(TimerStateEnum.STOPPED)
        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)
        assertThat(SUT.isPracticeComplete.getOrAwaitValue()).isFalse()

        setupInitialTimerState(TimerStateEnum.PAUSED)
        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)
        assertThat(SUT.isPracticeComplete.getOrAwaitValue()).isFalse()

        setupInitialTimerState(TimerStateEnum.ACTIVE)
        SUT = PracticeViewModel(repositoryMock, timerMock, resourceManagerMock, savedStateHandleMock)
        assertThat(SUT.isPracticeComplete.getOrAwaitValue()).isFalse()
    }

    @Test
    fun `onStart retrieves entry`() {
        SUT.onStart()

        val entryIdSlot = slot<Long>()
        coVerify (exactly = 1) { repositoryMock.getEntry(capture(entryIdSlot)) }
        assertThat(entryIdSlot.captured).isEqualTo(entryId)
    }

    @Test
    fun `onStart setsTimerValue with defaultValue`() {
        SUT.onStart()

        val timeSlot = slot<String>()
        coVerify (exactly = 1) { timerMock.setInitTime(capture(timeSlot)) }
        assertThat(timeSlot.captured).isEqualTo(DEFAULT_TIMER_VALUE)
    }

    @Test
    fun `setTimerValue sets init time with correct value`() {
        val time = "30"
        SUT.setTimerValue(time)

        verify (exactly = 1) { timerMock.setInitTime(time) }
    }

    @Test
    fun `setTimerValue sets timeOnScreen`() {
        val time = "5:00"
        SUT.setTimerValue(time)
        assertThat(SUT.timeOnScreen.value).isEqualTo(TimeInputUtil.secondsToTime(time.timeStringToSeconds()))
    }

    @Test
    fun `operateTimer updates event value if target tempo is required`() {
        every { timerMock.getCurrentState() } returns TimerStateEnum.STOPPED
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns true
            every { targetTempo } returns null
        }

        SUT.onStart()
        SUT.operateTimer()

        assertThat(SUT.event.value).isInstanceOf(PracticeViewModel.Event.TargetTempo::class.java)
        assertThat((SUT.event.value as PracticeViewModel.Event.TargetTempo).entryId).isEqualTo(entryId)
    }

    @Test
    fun `operateTimer calls playPause if target tempo is not required`() {
        // Target tempo is not null
        every { timerMock.getCurrentState() } returns TimerStateEnum.STOPPED
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns true
            every { targetTempo } returns 160
        }

        SUT.onStart()
        SUT.operateTimer()

        verify (exactly = 1) { timerMock.playPause() }

        // trackTempo is false
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns false
            every { targetTempo } returns 160
        }

        SUT.onStart()
        SUT.operateTimer()

        verify (exactly = 2) { timerMock.playPause() }

        // state is not stopped
        every { timerMock.getCurrentState() } returns TimerStateEnum.PAUSED
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns true
            every { targetTempo } returns 160
        }

        SUT.onStart()
        SUT.operateTimer()

        assertThat(timerMock.getCurrentState()).isEqualTo(TimerStateEnum.PAUSED)
        verify (exactly = 3) { timerMock.playPause() }
    }

    @Test
    fun `resetTimer calls rest`() {
        SUT.resetTimer()
        verify (exactly = 1) { timerMock.reset() }
    }

    @Test
    fun `finishPractice updates event to ToCurrentTempoScreen if track tempo is true`() {
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { trackTempo } returns true
        }

        SUT.onStart()
        SUT.finishPractice()

        assertThat(SUT.event.value).isInstanceOf(PracticeViewModel.Event.ToCurrentTempoScreen::class.java)
        assertThat((SUT.event.value as PracticeViewModel.Event.ToCurrentTempoScreen).entryId).isEqualTo(entryId)
    }

    @Test
    fun `finishPractice does not update event if track tempo is false`() {
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { trackTempo } returns false
        }

        SUT.onStart()
        SUT.finishPractice()

        verify (exactly = 0) { SUT.event.value }
    }

    @Test
    fun `finishPractice updates practice date`() {
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
            every { trackTempo } returns false
        }

        SUT.onStart()
        SUT.finishPractice()

        coVerify { repositoryMock.updatePracticeDate(SimpleDateFormat("dd-MM-yyyy").format(Date()), entryId) }
    }

    @Test
    fun `toReviewScreen updates event value to ToReviewScreen`() {
        coEvery { repositoryMock.getEntry(entryId) } returns mockk(relaxed = true) {
            every { id } returns entryId
        }

        SUT.onStart()
        SUT.toReviewScreen()

        assertThat(SUT.event.value).isInstanceOf(PracticeViewModel.Event.ToReviewScreen::class.java)
        assertThat((SUT.event.value as PracticeViewModel.Event.ToReviewScreen).entryId).isEqualTo(entryId)
    }

    @Test
    fun `toCustomTimeScreen updates event value to EnterCustomTime`() {
        SUT.toCustomTimeScreen()
        assertThat(SUT.event.value).isEqualTo(PracticeViewModel.Event.EnterCustomTime)
    }

    private fun getEntry(
        type: String = "test",
        author: String = "Author",
        name: String = "Name",
        state: EntryStateEnum = EntryStateEnum.ACTIVE,
        trackTempo: Boolean = true,
        currentTempo: Int? = 160,
        targetTempo: Int? = 180
    ) = Entry(
        id = entryId,
        type = type,
        author = author,
        name = name,
        state = state.name,
        trackTempo = trackTempo,
        currentTempo = currentTempo,
        targetTempo = targetTempo
    )

    private fun setupInitialTimerState(stateEnum: TimerStateEnum) {
        val currentState = MutableLiveData(stateEnum)
        every { timerMock.getStateAsLiveData()} returns currentState
    }
}