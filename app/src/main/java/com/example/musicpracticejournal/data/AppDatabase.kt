package com.example.musicpracticejournal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicpracticejournal.data.db.dao.EntryDao
import com.example.musicpracticejournal.data.db.dao.ReviewDao
import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.data.db.entity.Review

@Database(entities = [Entry::class, Review::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun reviewDao(): ReviewDao
}