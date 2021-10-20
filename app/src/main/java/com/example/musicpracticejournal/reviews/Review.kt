package com.example.musicpracticejournal.reviews

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "review")
@Parcelize
data class Review(
    val notesAccuracy:Float,
    val rhythm:Float,
    val articulation:Float,
    val dynamics:Float,
    val additionalNotes:String?,
    val practiceFragmentId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val created :Date = Date(Date().time),
): Parcelable