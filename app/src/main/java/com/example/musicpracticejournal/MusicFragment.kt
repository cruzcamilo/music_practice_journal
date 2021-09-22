package com.example.musicpracticejournal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musical_fragment_table")
data class MusicFragment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author:String,
    val name:String,
    val practiceTime:String,
    val date:String
)