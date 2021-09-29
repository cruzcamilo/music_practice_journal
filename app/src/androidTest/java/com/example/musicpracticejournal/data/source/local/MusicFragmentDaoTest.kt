package com.example.musicpracticejournal.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.musicpracticejournal.data.FragmentTypeEnum
import com.example.musicpracticejournal.data.MusicFragment
import com.example.musicpracticejournal.data.PracticeTimeEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
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
    private lateinit var database: MusicPracticeDb
    private lateinit var musicFragment: MusicFragment

    @Before
    fun init() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            MusicPracticeDb::class.java
        ).build()
        musicFragment = MusicFragment(FragmentTypeEnum.SONG.type, "ameseours", "sens",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().toString())
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMusicFragmentAndGetById() = runBlockingTest {
        val insertedFragmentId = database.musicFragmentDao().insertMusicFragment(musicFragment)
        val savedMusicFragment = database.musicFragmentDao().getMusicFragmentById(insertedFragmentId)

        assertThat(savedMusicFragment as MusicFragment, notNullValue())
        assertThat(savedMusicFragment.id, `is`(insertedFragmentId))
        assertThat(savedMusicFragment.type, `is`(musicFragment.type))
        assertThat(savedMusicFragment.author, `is`(musicFragment.author))
        assertThat(savedMusicFragment.name, `is`(musicFragment.name))
        assertThat(savedMusicFragment.practiceTime, `is`(musicFragment.practiceTime))
    }

    @Test
    fun updateMusicFragmentAndGetById() = runBlockingTest {
        val musicFragmentId = database.musicFragmentDao().insertMusicFragment(musicFragment)
        val musicFragmentUpdate = MusicFragment(FragmentTypeEnum.EXERCISE.type, "Ameseours", "heurt", PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().toString(), musicFragmentId)

        database.musicFragmentDao().updateMusicFragment(musicFragmentUpdate)

        val updatedMusicFragment = database.musicFragmentDao().getMusicFragmentById(musicFragmentId)

        assertThat(updatedMusicFragment as MusicFragment, notNullValue())
        assertThat(updatedMusicFragment.id, `is`(musicFragmentId))
        assertThat(updatedMusicFragment.type, `is`(musicFragmentUpdate.type))
        assertThat(updatedMusicFragment.author, `is`(musicFragmentUpdate.author))
        assertThat(updatedMusicFragment.name, `is`(musicFragmentUpdate.name))
        assertThat(updatedMusicFragment.practiceTime, `is`(musicFragmentUpdate.practiceTime))
    }

    @Test
    fun deleteAllMusicFragments() = runBlockingTest {
        val fragmentList = database.musicFragmentDao().getAllMusicFragments()
        database.musicFragmentDao().insertMusicFragment(musicFragment)
        val list = fragmentList.take(1).toList()[0]

        assertThat(list,  notNullValue())
        assertThat(list,  not(emptyList()))
        assertThat(list.size, `is`(1))

        database.musicFragmentDao().deleteAlLMusicFragments()
        val deletedList = fragmentList.take(1).toList()[0]
        assertThat(deletedList,  `is`(emptyList()))
    }

}