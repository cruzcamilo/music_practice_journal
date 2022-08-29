package com.example.musicpracticejournal.domain.entity

import android.content.Context
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.extensions.visibleOrGone

data class EntryItem (
    val type: String,
    val author: String,
    val name: String,
    val state: String,
    val targetTempo: String = "",
    val currentTempo: String = "",
    val id: Long?,
    val updated: String = "",
) {
    fun getUpdatedVisibility(): Int = visibleOrGone(updated.isNotEmpty())
    fun getTempoVisibility(): Int = visibleOrGone(currentTempo.isNotEmpty() && targetTempo.isNotEmpty())
    fun getTitle(): String = "$author - $name"
    fun getLastPractice(context: Context): String = context.resources.getString(
        R.string.last_practice_item,
        updated
    )
    fun setBpmText(tempo: String): String = "$tempo bpm"
}