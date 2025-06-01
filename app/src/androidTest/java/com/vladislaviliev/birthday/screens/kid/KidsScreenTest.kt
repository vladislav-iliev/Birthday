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
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.State
import com.vladislaviliev.birthday.kid.text.Text
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
        val networkText = Text("Test Kid", Age(1, false), Theme.PELICAN)
        val yearsText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)

        val state = State(true, networkText, null)
        composeTestRule.setContent { KidScreen(state, {}) }

        composeTestRule
            .onNodeWithText(context.getString(R.string.today_x_is, networkText.name), ignoreCase = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("1").assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearsText), ignoreCase = true).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.avatar_image_of_x, networkText.name))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.select_new_avatar))
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.nanit)).assertIsDisplayed()
    }

    @Test
    fun testPlurals_months() {
        val state = State(true, Text("Test Kid", Age(1, true), Theme.PELICAN), null)
        composeTestRule.setContent { KidScreen(state, {}) }

        val monthText = context.resources.getQuantityString(R.plurals.plurals_month, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, monthText), ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testPlurals_years() {
        val state = State(true, Text("Test Kid", Age(1, false), Theme.PELICAN), null)
        composeTestRule.setContent { KidScreen(state, {}) }

        val yearText = context.resources.getQuantityString(R.plurals.plurals_year, 1, 1)
        composeTestRule
            .onNodeWithText(context.getString(R.string.x_old, yearText), ignoreCase = true).assertIsDisplayed()
    }
}