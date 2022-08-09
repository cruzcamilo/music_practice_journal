package com.example.musicpracticejournal.screens.create

import com.example.musicpracticejournal.BaseViewModelTest
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.domain.ResourceManager
import com.example.musicpracticejournal.domain.usecase.CreateEntryUseCase
import com.example.musicpracticejournal.practicefragments.EntryTypeEnum
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
class CreateViewModelTest: BaseViewModelTest() {

    private val createEntryUseCaseMock: CreateEntryUseCase = mockk(relaxed = true)
    private val resourceManagerMock: ResourceManager = mockk(relaxed = true)
    private lateinit var SUT: CreateViewModel

    @Before
    fun setUp() {
        SUT = CreateViewModel(createEntryUseCaseMock, resourceManagerMock)
    }

    @Test
    fun `saveButtonEnabled is true when values are not empty`() {
        initializeValues()

        val result = SUT.saveButtonEnabled.getOrAwaitValue()

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `saveButtonEnabled is false when author is empty`() {
        initializeValues(author = "")

        val result = SUT.saveButtonEnabled.getOrAwaitValue()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `saveButtonEnabled is false when name is empty`() {
        initializeValues(name = "")

        val result = SUT.saveButtonEnabled.getOrAwaitValue()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `saveButtonEnabled is false when practice state is empty`() {
        initializeValues(practiceState = "")

        val result = SUT.saveButtonEnabled.getOrAwaitValue()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `songTechniqueHint is song_hint when type is song`() {
        val expectedHint = "Song name"
        coEvery { resourceManagerMock.getString(R.string.song_hint) } returns expectedHint
        SUT.type.value = EntryTypeEnum.SONG.type

        val hint = SUT.songTechniqueHint.getOrAwaitValue()

        assertThat(hint).isEqualTo(expectedHint)
    }

    @Test
    fun `songTechniqueHint is technique_hint when type is not song`() {
        val expectedHint = "Technique"
        coEvery { resourceManagerMock.getString(R.string.technique_hint) } returns expectedHint
        SUT.type.value = EntryTypeEnum.EXERCISE.type

        val hint = SUT.songTechniqueHint.getOrAwaitValue()

        assertThat(hint).isEqualTo(expectedHint)
    }

    @Test
    fun `createNameHint is section_hint when type is song`() {
        val expectedHint = "Section"
        coEvery { resourceManagerMock.getString(R.string.section_hint) } returns expectedHint
        SUT.type.value = EntryTypeEnum.SONG.type

        val hint = SUT.createNameHint.getOrAwaitValue()

        assertThat(hint).isEqualTo(expectedHint)
    }

    @Test
    fun `createNameHint is section_hint when type is exercise`() {
        val expectedHint = "Name"
        coEvery { resourceManagerMock.getString(R.string.name_hint) } returns expectedHint
        SUT.type.value = EntryTypeEnum.EXERCISE.type

        val hint = SUT.createNameHint.getOrAwaitValue()

        assertThat(hint).isEqualTo(expectedHint)
    }

    @Test
    fun `save uses correct params to create entry`() {
        initializeValues()
        SUT.trackTempo.value = true
        val slot = slot<CreateEntryUseCase.Params>()
        coEvery { createEntryUseCaseMock(capture(slot)) } just runs

        SUT.save()

        coVerify (exactly = 1) { createEntryUseCaseMock.invoke(any()) }

        val entryCaptured = slot.captured.entry
        assertThat(SUT.type.getOrAwaitValue()).isEqualTo(entryCaptured.type)
        assertThat(SUT.author.getOrAwaitValue()).isEqualTo(entryCaptured.author)
        assertThat(SUT.name.getOrAwaitValue()).isEqualTo(entryCaptured.name)
        assertThat(SUT.practiceState.getOrAwaitValue()).isEqualTo(entryCaptured.state)
        assertThat(SUT.trackTempo.getOrAwaitValue()).isEqualTo(entryCaptured.trackTempo)
    }

    @Test
    fun `save updates event value to ToHomeScreen`() {
        initializeValues()
        val entry = Entry(
            SUT.type.value!!,
            SUT.author.value!!,
            SUT.name.value!!,
            SUT.practiceState.value!!,
            false
        )

        coEvery { createEntryUseCaseMock(CreateEntryUseCase.Params(entry)) } just runs
        SUT.save()

        assertThat(SUT.event.value).isEqualTo(CreateViewModel.Event.ToHomeScreen)
    }

    private fun initializeValues(
        author: String = "author",
        name: String = "name",
        practiceState: String = "active"
    ) {
        SUT.author.value = author
        SUT.name.value = name
        SUT.practiceState.value = practiceState
    }
}