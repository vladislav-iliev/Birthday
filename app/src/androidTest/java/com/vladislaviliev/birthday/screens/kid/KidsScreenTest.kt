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

    private val defaultState = KidScreenState(true, "Test Kid", 1, Theme.PELICAN, null)

    @Test
    fun testScreenStructure_coreElements() {
        val message = Message(name = "Test Kid", ageMonths = 12, theme = Theme.FOX)
        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)

        composeTestRule.setContent { KidScreen(defaultState, {}) }

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
        composeTestRule.setContent { KidScreen(defaultState, {}) }

        val monthText = context.resources.getQuantityString(R.plurals.plurals_month, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, monthText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_elevenMonths() {
        composeTestRule.setContent { KidScreen(defaultState, {}) }

        val monthsText = context.resources.getQuantityString(R.plurals.plurals_month, 11, 11)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, monthsText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_oneYear() {
        composeTestRule.setContent { KidScreen(defaultState, {}) }

        val yearText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_oneYearAndOneMonth() {
        composeTestRule.setContent { KidScreen(defaultState, {}) }
        val yearText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_twoYears() {
        composeTestRule.setContent { KidScreen(defaultState, {}) }

        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 2, 2)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_twoYearsAndThreeMonths() {
        composeTestRule.setContent { KidScreen(defaultState, {}) }

        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 2, 2)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
    }
}