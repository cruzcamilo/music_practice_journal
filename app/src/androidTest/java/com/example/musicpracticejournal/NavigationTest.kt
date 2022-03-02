package com.example.musicpracticejournal

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.musicpracticejournal.screens.home.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
class NavigationTest {

    @Test
    fun clickTask_navigateToDetailFragmentOne() {
        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_AppCompat_Light)
        val navController = mock(NavController::class.java)

        Thread.sleep(2000)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.fab_button)).perform(click())
        verify(navController).navigate(R.id.toCreateFragment)
    }

}