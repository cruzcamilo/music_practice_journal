package com.example.musicpracticejournal

import com.example.musicpracticejournal.util.TimeInputUtil
import com.example.musicpracticejournal.util.addInitialZero
import com.example.musicpracticejournal.util.secondsToMinutesSeconds
import com.example.musicpracticejournal.util.timeStringToSeconds
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExtensionFunctionsTest {

    @Test
    fun add_initial_zero() {
        for (i in 1..9) {
            assertEquals("0$i", i.addInitialZero())
        }
        assertEquals("10", 10.addInitialZero())
    }

    @Test
    fun minutes_seconds_to_seconds() {
        assertEquals(1L, "00:01".timeStringToSeconds())
        assertEquals(61, "01:01".timeStringToSeconds())
        assertEquals(659L, "10:59".timeStringToSeconds())
    }

    @Test
    fun test_time_input () {
        assertEquals("10:01", TimeInputUtil.append("1", "01:00"))
        assertEquals("10:00", TimeInputUtil.append("1", "10:00"))
        assertEquals("02:00", TimeInputUtil.append("00", "00:02"))
    }

    @Test
    fun test_time_delete () {
        assertEquals("00:00", TimeInputUtil.delete( "00:02"))
        assertEquals("00:22", TimeInputUtil.delete( "02:20"))
        assertEquals("10:00", TimeInputUtil.append("1", "10:00"))
        assertEquals("02:00", TimeInputUtil.append("00", "00:02"))
    }

    @Test
    fun seconds_to_minutes_seconds_test() {
        assertEquals("00:01", 1L.secondsToMinutesSeconds())
        assertEquals("01:01", 61L.secondsToMinutesSeconds())
        assertEquals("10:59", 659L.secondsToMinutesSeconds())
    }
}