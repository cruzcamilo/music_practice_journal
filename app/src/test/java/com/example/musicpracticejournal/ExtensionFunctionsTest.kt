package com.example.musicpracticejournal

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
    fun seconds_to_minutes_seconds() {
        assertEquals("00:01", 1L.secondsToMinutesSeconds())
        assertEquals("01:01", 61L.secondsToMinutesSeconds())
        assertEquals("10:59", 659L.secondsToMinutesSeconds())
    }
}