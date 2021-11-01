package com.example.musicpracticejournal.practicefragments

enum class PracticeStateEnum(val state: String) {
    QUEUED ("Queued"),
    ACTIVE("Active"),
    COMPLETE("Completed");

    override fun toString() : String {
        return state
    }
}