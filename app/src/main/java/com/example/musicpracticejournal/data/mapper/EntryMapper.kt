package com.example.musicpracticejournal.data.mapper

import com.example.musicpracticejournal.data.db.entity.Entry
import com.example.musicpracticejournal.domain.entity.EntryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<Entry>>.toDomain() =
    this.map { list ->
        list.map {
            it.toDomain()
        }
    }

private fun Entry.toDomain() = EntryItem(
    type = type,
    author = author,
    name = name,
    state = state,
    targetTempo = targetTempo?.toString() ?: "",
    currentTempo = currentTempo?.toString() ?: "",
    id = id
)