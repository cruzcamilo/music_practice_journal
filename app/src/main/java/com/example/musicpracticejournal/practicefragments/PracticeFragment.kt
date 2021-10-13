package com.example.musicpracticejournal.practicefragments

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "musical_fragment_table")
@Parcelize
data class PracticeFragment(
    val type:String,
    val author:String,
    val name:String,
    val practiceTime:String,
    val practiceDate:String,
    val targetTempo: Int? = null,
    val currentTempo: Int?= null,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var created :Date = Date(Date().time),
    val updated :Date? = null,
    val totalPracticeTimeInSeconds: Long = 0,
): Parcelable