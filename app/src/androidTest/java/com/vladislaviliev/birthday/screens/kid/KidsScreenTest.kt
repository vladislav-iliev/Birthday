package com.vladislaviliev.birthday.screens.kid

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Message
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KidScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testScreenStructure_coreElements() {
        val message = Message(name = "Test Kid", ageMonths = 12, theme = Theme.FOX)
        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)

        composeTestRule.setContent { KidScreen(message, {}) }

        composeTestRule
            .onNodeWithText(context.getString(R.string.today_x_is, message.name), ignoreCase = true).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("1").assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.avatar_image_of_x, message.name))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.select_new_avatar))
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.nanit)).assertIsDisplayed()
    }

    @Test
    fun testPlurals_oneMonth() {
        val message = Message(name = "Baby", ageMonths = 1, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }

        val monthText = context.resources.getQuantityString(R.plurals.plurals_month, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, monthText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_elevenMonths() {
        val message = Message(name = "Baby", ageMonths = 11, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }

        val monthsText = context.resources.getQuantityString(R.plurals.plurals_month, 11, 11)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, monthsText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_oneYear() {
        val message = Message(name = "Toddler", ageMonths = 12, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }

        val yearText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_oneYearAndOneMonth() {
        val message = Message(name = "Toddler", ageMonths = 13, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }
        val yearText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_twoYears() {
        val message = Message(name = "Kid", ageMonths = 24, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }

        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 2, 2)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_twoYearsAndThreeMonths() {
        val message = Message(name = "Kid", ageMonths = 27, theme = Theme.FOX)

        composeTestRule.setContent { KidScreen(message, {}) }

        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 2, 2)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
    }
}