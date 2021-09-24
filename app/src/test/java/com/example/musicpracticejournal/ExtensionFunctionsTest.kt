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
}