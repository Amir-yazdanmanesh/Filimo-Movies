package com.test.filimo

import android.view.KeyEvent
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.filimo.di.AppComponentProvider
import com.test.movies.MoviesFragment
import com.test.movies.R
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MoviesFragmentInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var scenario: FragmentScenario<MoviesFragment>

    @Before
    fun setUp() {
        // Create a test-specific component and inject the test-specific module
        scenario = launchFragmentInContainer<MoviesFragment>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return when (className) {
                        MoviesFragment::class.java.name -> {
                            val fragment = MoviesFragment()
                            AppComponentProvider.appComponent().moviesComponent().create()
                                .inject(fragment)
                            fragment
                        }
                        else -> super.instantiate(classLoader, className)
                    }
                }
            }
        )
    }

    @Test
    fun testEmptySearch() {
        // Search for empty query
        onView(withId(R.id.search_movies)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(pressKey(KeyEvent.KEYCODE_ENTER))

        // Check if empty list is displayed
        onView(withId(R.id.empty_list)).check(matches(isDisplayed()))
        onView(withId(R.id.movies_shimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_list_text)).check(matches(withText(R.string.empty_list_text)))
    }

    @Test
    fun testSearch() {
        // Search for movies
        onView(withId(R.id.search_movies)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(
            typeText("A"),
        )
        // Wait for onQueryTextListener call getMoviesEvent
        Thread.sleep(1500)
        onView(withId(R.id.movies_shimmer)).check(matches(isDisplayed()))
        // Wait for load data
        Thread.sleep(3000)
        onView(withId(R.id.movies_shimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        // Check if the RecyclerView has items
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)))
    }
}
