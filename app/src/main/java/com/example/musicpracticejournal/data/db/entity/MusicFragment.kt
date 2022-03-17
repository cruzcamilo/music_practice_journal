package com.example.musicpracticejournal.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "musical_fragment_table")
@Parcelize
data class MusicFragment(
    val type: String,
    val author: String,
    val name: String,
    val state: String,
    val targetTempo: Int? = null,
    val currentTempo: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val practiceTime: String? = null,
    val created: Date = Date(Date().time),
    val updated: String? = null,
    val totalPracticeTimeInSeconds: Long = 0,
) : Parcelable