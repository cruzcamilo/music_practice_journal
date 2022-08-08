package com.example.musicpracticejournal.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.practicefragments.EntryStateEnum
import com.example.musicpracticejournal.practicefragments.EntryTypeEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class EntryDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var entry: Entry

    @Before
    fun init() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).build()
        entry = Entry(
            EntryTypeEnum.SONG.type, "ameseours", "sens",
            EntryStateEnum.ACTIVE.name, false
        )
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMusicFragmentAndGetById() = runTest {
        val insertedFragmentId = database.entryDao().saveEntry(entry)
        val savedMusicFragment = database.entryDao().getEntryById(insertedFragmentId)

        assertThat(savedMusicFragment as Entry, notNullValue())
        assertThat(savedMusicFragment.id, `is`(insertedFragmentId))
        assertThat(savedMusicFragment.type, `is`(entry.type))
        assertThat(savedMusicFragment.author, `is`(entry.author))
        assertThat(savedMusicFragment.name, `is`(entry.name))
        assertThat(savedMusicFragment.state, `is`(entry.state))
    }

    @Test
    fun updateMusicFragmentAndGetById() = runTest {
        val musicFragmentId = database.entryDao().saveEntry(entry)
        val entryUpdate = Entry(
            EntryTypeEnum.EXERCISE.type, "Ameseours", "heurt",
            EntryStateEnum.COMPLETE.name, true,
            160, 160, musicFragmentId)

        database.entryDao().updateEntry(entryUpdate)

        val updatedMusicFragment = database.entryDao().getEntryById(musicFragmentId)

        assertThat(updatedMusicFragment as Entry, notNullValue())
        assertThat(updatedMusicFragment.id, `is`(musicFragmentId))
        assertThat(updatedMusicFragment.type, `is`(entryUpdate.type))
        assertThat(updatedMusicFragment.author, `is`(entryUpdate.author))
        assertThat(updatedMusicFragment.name, `is`(entryUpdate.name))
        assertThat(updatedMusicFragment.state, `is`(entryUpdate.state))
    }

    @Test
    fun deleteAllMusicFragments() = runTest {
        val fragmentList = database.entryDao().getAllEntries()
        database.entryDao().saveEntry(entry)
        val list = fragmentList.take(1).toList()[0]

        assertThat(list,  notNullValue())
        assertThat(list,  not(emptyList()))
        assertThat(list.size, `is`(1))

        database.entryDao().deleteEntries()
        val deletedList = fragmentList.take(1).toList()[0]
        assertThat(deletedList,  `is`(emptyList()))
    }

}