package com.example.musicpracticejournal.domain.entity

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
}