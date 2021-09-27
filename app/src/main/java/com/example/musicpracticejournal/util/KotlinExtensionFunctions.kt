package com.example.musicpracticejournal

fun Int.addInitialZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}