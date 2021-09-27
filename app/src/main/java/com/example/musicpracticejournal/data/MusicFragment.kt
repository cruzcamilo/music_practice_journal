package com.example.musicpracticejournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musical_fragment_table")
data class MusicFragment(
    val type:String,
    val author:String,
    val name:String,
    val practiceTime:String,
    val practiceDate:String
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}