package com.example.musicpracticejournal.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicpracticejournal.MusicFragment
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(MusicFragment::class), version = 1, exportSchema = false)
abstract class MusicPracticeDb : RoomDatabase() {
    abstract fun musicFragmentDao(): MusicFragmentDao

    companion object {
        @Volatile
        private var INSTANCE: MusicPracticeDb? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MusicPracticeDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicPracticeDb::class.java,
                    "word_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}