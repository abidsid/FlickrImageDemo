package com.flickr.demo.flickrdemo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flickr.demo.flickrdemo.data.model.Items
import com.flickr.demo.flickrdemo.data.model.Media
import com.flickr.demo.flickrdemo.data.model.Thumbnail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testDetailsScreenDisplaysCorrectInfo() {
        // Launch the details screen with test data
        val testThumbnail = Thumbnail(
            link = "https://example.com",
            title = "Sample Thumbnail",
            items = listOf(
                Items(
                    title = "Sample Item",
                    link = "https://example.com/item",
                    date_taken = "2025-01-22",
                    description = "Sample Description",
                    media = Media("https://example.com/image.jpg")
                )
            )
        )

        // Check that the title is displayed
        onView(withText("Sample Thumbnail")).check(matches(isDisplayed()))

        // Check that the description is displayed
        onView(withText("Sample Description")).check(matches(isDisplayed()))

        // Check that the author and date are displayed
        onView(withText("Author: Sample Item")).check(matches(isDisplayed()))
        onView(withText("Published: 2025-01-22")).check(matches(isDisplayed()))
    }
}