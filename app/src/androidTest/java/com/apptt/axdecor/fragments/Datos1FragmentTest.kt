package com.apptt.axdecor.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Datos1FragmentTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onViewCreated() {
        val fragment = Datos1Fragment()

        //onView(withId(R.id.btnSiguiente)).perform(click()).check(withId(com.google.android.material.R.id.snackbar_text).matches())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Parece que olvidas algo.")))
    }
}