package com.example.musicpracticejournal.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.practicefragments.PracticeTimeEnum
import com.example.musicpracticejournal.practicefragments.PracticeTypeEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

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
        entry = Entry(PracticeTypeEnum.SONG.type, "ameseours", "sens",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString())
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMusicFragmentAndGetById() = runBlockingTest {
        val insertedFragmentId = database.practiceFragmentDao().savePracticeFragment(entry)
        val savedMusicFragment = database.practiceFragmentDao().getPracticeFragmentById(insertedFragmentId)

        assertThat(savedMusicFragment as Entry, notNullValue())
        assertThat(savedMusicFragment.id, `is`(insertedFragmentId))
        assertThat(savedMusicFragment.type, `is`(entry.type))
        assertThat(savedMusicFragment.author, `is`(entry.author))
        assertThat(savedMusicFragment.name, `is`(entry.name))
        assertThat(savedMusicFragment.practiceTime, `is`(entry.practiceTime))
    }

    @Test
    fun updateMusicFragmentAndGetById() = runBlockingTest {
        val musicFragmentId = database.practiceFragmentDao().savePracticeFragment(entry)
        val entryUpdate = Entry(
            PracticeTypeEnum.EXERCISE.type, "Ameseours", "heurt",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString(),
            null, null, musicFragmentId)

        database.practiceFragmentDao().updatePracticeFragment(entryUpdate)

        val updatedMusicFragment = database.practiceFragmentDao().getPracticeFragmentById(musicFragmentId)

        assertThat(updatedMusicFragment as Entry, notNullValue())
        assertThat(updatedMusicFragment.id, `is`(musicFragmentId))
        assertThat(updatedMusicFragment.type, `is`(entryUpdate.type))
        assertThat(updatedMusicFragment.author, `is`(entryUpdate.author))
        assertThat(updatedMusicFragment.name, `is`(entryUpdate.name))
        assertThat(updatedMusicFragment.practiceTime, `is`(entryUpdate.practiceTime))
    }

    @Test
    fun deleteAllMusicFragments() = runBlockingTest {
        val fragmentList = database.practiceFragmentDao().getAll()
        database.practiceFragmentDao().savePracticeFragment(entry)
        val list = fragmentList.take(1).toList()[0]

        assertThat(list,  notNullValue())
        assertThat(list,  not(emptyList()))
        assertThat(list.size, `is`(1))

        database.practiceFragmentDao().deleteAlLMusicFragments()
        val deletedList = fragmentList.take(1).toList()[0]
        assertThat(deletedList,  `is`(emptyList()))
    }

}