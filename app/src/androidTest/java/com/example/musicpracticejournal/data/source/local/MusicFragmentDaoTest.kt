package com.example.musicpracticejournal.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.musicpracticejournal.data.AppDatabase
import com.example.musicpracticejournal.data.db.entity.MusicFragment
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
    private lateinit var musicFragment: MusicFragment

    @Before
    fun init() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).build()
        musicFragment = MusicFragment(PracticeTypeEnum.SONG.type, "ameseours", "sens",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString())
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMusicFragmentAndGetById() = runBlockingTest {
        val insertedFragmentId = database.practiceFragmentDao().savePracticeFragment(musicFragment)
        val savedMusicFragment = database.practiceFragmentDao().getMusicFragmentById(insertedFragmentId)

        assertThat(savedMusicFragment as MusicFragment, notNullValue())
        assertThat(savedMusicFragment.id, `is`(insertedFragmentId))
        assertThat(savedMusicFragment.type, `is`(musicFragment.type))
        assertThat(savedMusicFragment.author, `is`(musicFragment.author))
        assertThat(savedMusicFragment.name, `is`(musicFragment.name))
        assertThat(savedMusicFragment.practiceTime, `is`(musicFragment.practiceTime))
    }

    @Test
    fun updateMusicFragmentAndGetById() = runBlockingTest {
        val musicFragmentId = database.practiceFragmentDao().savePracticeFragment(musicFragment)
        val musicFragmentUpdate = MusicFragment(
            PracticeTypeEnum.EXERCISE.type, "Ameseours", "heurt",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString(),
            null, null, musicFragmentId)

        database.practiceFragmentDao().updateMusicFragment(musicFragmentUpdate)

        val updatedMusicFragment = database.practiceFragmentDao().getMusicFragmentById(musicFragmentId)

        assertThat(updatedMusicFragment as MusicFragment, notNullValue())
        assertThat(updatedMusicFragment.id, `is`(musicFragmentId))
        assertThat(updatedMusicFragment.type, `is`(musicFragmentUpdate.type))
        assertThat(updatedMusicFragment.author, `is`(musicFragmentUpdate.author))
        assertThat(updatedMusicFragment.name, `is`(musicFragmentUpdate.name))
        assertThat(updatedMusicFragment.practiceTime, `is`(musicFragmentUpdate.practiceTime))
    }

    @Test
    fun deleteAllMusicFragments() = runBlockingTest {
        val fragmentList = database.practiceFragmentDao().getAll()
        database.practiceFragmentDao().savePracticeFragment(musicFragment)
        val list = fragmentList.take(1).toList()[0]

        assertThat(list,  notNullValue())
        assertThat(list,  not(emptyList()))
        assertThat(list.size, `is`(1))

        database.practiceFragmentDao().deleteAlLMusicFragments()
        val deletedList = fragmentList.take(1).toList()[0]
        assertThat(deletedList,  `is`(emptyList()))
    }

}