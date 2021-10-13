package com.example.musicpracticejournal.practicefragments

enum class PracticeTypeEnum(val type: String) {
    SONG("Song"),
    EXERCISE("Exercise");

    override fun toString() : String {
        return type
    }
}