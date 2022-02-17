package com.example.musicpracticejournal.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.musicpracticejournal.data.MusicPracticeDb
import com.example.musicpracticejournal.data.db.entity.PracticeFragment
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
    private lateinit var database: MusicPracticeDb
    private lateinit var practiceFragment: PracticeFragment

    @Before
    fun init() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            MusicPracticeDb::class.java
        ).build()
        practiceFragment = PracticeFragment(PracticeTypeEnum.SONG.type, "ameseours", "sens",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString())
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMusicFragmentAndGetById() = runBlockingTest {
        val insertedFragmentId = database.musicFragmentDao().savePracticeFragment(practiceFragment)
        val savedMusicFragment = database.musicFragmentDao().getMusicFragmentById(insertedFragmentId)

        assertThat(savedMusicFragment as PracticeFragment, notNullValue())
        assertThat(savedMusicFragment.id, `is`(insertedFragmentId))
        assertThat(savedMusicFragment.type, `is`(practiceFragment.type))
        assertThat(savedMusicFragment.author, `is`(practiceFragment.author))
        assertThat(savedMusicFragment.name, `is`(practiceFragment.name))
        assertThat(savedMusicFragment.practiceTime, `is`(practiceFragment.practiceTime))
    }

    @Test
    fun updateMusicFragmentAndGetById() = runBlockingTest {
        val musicFragmentId = database.musicFragmentDao().savePracticeFragment(practiceFragment)
        val musicFragmentUpdate = PracticeFragment(
            PracticeTypeEnum.EXERCISE.type, "Ameseours", "heurt",
            PracticeTimeEnum.FIFTEEN.toString(), Calendar.getInstance().time.toString(),
            null, null, musicFragmentId)

        database.musicFragmentDao().updateMusicFragment(musicFragmentUpdate)

        val updatedMusicFragment = database.musicFragmentDao().getMusicFragmentById(musicFragmentId)

        assertThat(updatedMusicFragment as PracticeFragment, notNullValue())
        assertThat(updatedMusicFragment.id, `is`(musicFragmentId))
        assertThat(updatedMusicFragment.type, `is`(musicFragmentUpdate.type))
        assertThat(updatedMusicFragment.author, `is`(musicFragmentUpdate.author))
        assertThat(updatedMusicFragment.name, `is`(musicFragmentUpdate.name))
        assertThat(updatedMusicFragment.practiceTime, `is`(musicFragmentUpdate.practiceTime))
    }

    @Test
    fun deleteAllMusicFragments() = runBlockingTest {
        val fragmentList = database.musicFragmentDao().getAllMusicFragments()
        database.musicFragmentDao().savePracticeFragment(practiceFragment)
        val list = fragmentList.take(1).toList()[0]

        assertThat(list,  notNullValue())
        assertThat(list,  not(emptyList()))
        assertThat(list.size, `is`(1))

        database.musicFragmentDao().deleteAlLMusicFragments()
        val deletedList = fragmentList.take(1).toList()[0]
        assertThat(deletedList,  `is`(emptyList()))
    }

}