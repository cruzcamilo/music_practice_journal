package com.example.musicpracticejournal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicpracticejournal.data.db.dao.PracticeFragmentDao
import com.example.musicpracticejournal.data.db.dao.ReviewDao
import com.example.musicpracticejournal.data.db.entity.MusicFragment
import com.example.musicpracticejournal.data.db.entity.Review

@Database(entities = [MusicFragment::class, Review::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun practiceFragmentDao(): PracticeFragmentDao
    abstract fun reviewDao(): ReviewDao
}