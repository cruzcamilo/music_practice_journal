package com.example.musicpracticejournal.util

import android.content.Context
import android.util.Log
import com.example.musicpracticejournal.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Int
 */
fun Int.addInitialZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun Int?.setBpmInformation(context: Context): String {
    this.apply {
        return if (this == null) context.getString(R.string.no_data)
        else context.getString(R.string.bpm_amount, this.toString())
    }
}

/**
 * String
 */

fun String.timeStringToSeconds(): Long {
    val splitUnits = this.split(":")
    var result = 0L
    try {
        result = if (splitUnits.size == 2) {
            val minutesMillis = splitUnits[0].toLong() * 60
            val secondsMillis = splitUnits[1].toLong()
            minutesMillis + secondsMillis
        } else  {
            splitUnits[0].toLong()
        }
    } catch (e: Exception) {
        Log.e("Min Sec Error", e.message.toString())
    }
    return result
}

fun String.minsToSeconds(): Long {
    return this.toLong() * 60
}

fun String.formatDate(): String {
    val f = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return f.format(this)?:""
}

/**
 * Long
 */

fun Long.secondsToMinutesSeconds(): String {
    val f = DecimalFormat("00")
    val min = this / 60
    val sec = this % 60
    return f.format(min) + ":" + f.format(sec)
}