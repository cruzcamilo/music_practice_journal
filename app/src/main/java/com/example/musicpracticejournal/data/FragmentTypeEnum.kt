package com.example.musicpracticejournal.data

enum class FragmentTypeEnum(val type: String) {
    SONG("Song"),
    EXERCISE("Exercise");

    override fun toString() : String {
        return type
    }
}