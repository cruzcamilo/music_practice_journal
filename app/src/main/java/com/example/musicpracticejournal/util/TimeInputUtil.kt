package com.example.musicpracticejournal.util

import java.text.DecimalFormat

object TimeInputUtil {

    private val f = DecimalFormat("00")

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

    fun secondsToTime(remainingSeconds: Long): String {
        val minutes = with(remainingSeconds / 60) {
            if (this > 0) this.toString() else ""
        }

        val seconds = with(remainingSeconds%60) {
            if (minutes.isNotEmpty()) {
                ":${f.format(this)}"
            } else {
                this.toString()
            }
        }
        return minutes + seconds
    }
}