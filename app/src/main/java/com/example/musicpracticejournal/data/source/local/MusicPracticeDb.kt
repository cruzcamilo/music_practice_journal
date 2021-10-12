package com.example.musicpracticejournal.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicpracticejournal.data.PracticeFragment
import kotlinx.coroutines.CoroutineScope

@Database(entities = [PracticeFragment::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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