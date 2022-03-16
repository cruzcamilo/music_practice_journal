package com.example.musicpracticejournal.util

object TimeInputUtil {

    fun append(input: String, currentText: String): String {
        return if (currentText[0].toString() == "0") {
            currentText.replace(":", "").drop(input.length).plus(input)
                .replaceRange(2, 2, ":")
        } else {
            currentText
        }
    }

    fun delete(currentText: String): String {
        return if (currentText != "00:00") {
            currentText.replace(":", "")
                .replaceRange(0, 0, "0")
                .dropLast(1)
                .replaceRange(2, 2, ":")
        } else {
            currentText
        }
    }
}