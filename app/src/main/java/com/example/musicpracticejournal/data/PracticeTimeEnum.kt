package com.example.musicpracticejournal.data

enum class PracticeTimeEnum(val minutes:Int) {
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20);

    override fun toString() : String {
        return minutes.toString()
    }
}