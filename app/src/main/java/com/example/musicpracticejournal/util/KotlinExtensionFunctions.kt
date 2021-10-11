package com.example.musicpracticejournal

import android.util.Log
import java.text.DecimalFormat

fun Int.addInitialZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun String.timeStringToSeconds():Long {
    val splitUnits = this.split(":")
    var result = 0L
    try {
        val minutesMillis = splitUnits[0].toLong()*60
        val secondsMillis = splitUnits[1].toLong()
        result = minutesMillis+secondsMillis
    } catch (e: Exception) {
        Log.e("Min Sec Error", e.message.toString())
    }
    return result
}

fun Long.secondsToMinutesSeconds():String {
    val f = DecimalFormat("00")
    val min = this / 60
    val sec = this % 60
    return f.format(min) + ":" + f.format(sec)
}