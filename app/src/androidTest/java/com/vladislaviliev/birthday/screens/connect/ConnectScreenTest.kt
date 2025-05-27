package com.vladislaviliev.birthday.screens.connect

import android.content.Context
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.networking.State
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConnectScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getStringResource(id: Int) = ApplicationProvider.getApplicationContext<Context>().getString(id)

    @Test
    fun testShowsInitialElements() {
        composeTestRule.setContent { ConnectScreen({ _, _ -> }, State.Disconnected()) }
        composeTestRule.onNodeWithText(getStringResource(R.string.connect_to_server)).assertIsDisplayed()
        composeTestRule.onNodeWithText("localhost").assertIsDisplayed()
        composeTestRule.onNodeWithText("8080").assertIsDisplayed()
        composeTestRule.onNodeWithText(getStringResource(R.string.connect)).assertIsDisplayed()
        composeTestRule.onRoot().onChildren().assertCountEquals(4)
    }

    @Test
    fun testOnConnectClick() {
        var ip = ""
        var port = 0
        val connect: (String, Int) -> Unit = { ipArg, portArg -> ip = ipArg; port = portArg }

        var testIp = "localhost"
        var testPort = 8080

        composeTestRule.setContent { ConnectScreen(connect, State.Disconnected()) }
        composeTestRule.onNodeWithText(getStringResource(R.string.connect)).performClick()

        Assert.assertEquals(testIp, ip)
        Assert.assertEquals(testPort, port)

        var newTestIp = ""
        var newTestPort = -1

        composeTestRule.onNodeWithText(testIp).performTextInput(newTestIp)
        composeTestRule.onNodeWithText(getStringResource(R.string.connect)).performClick()

        Assert.assertNotEquals(newTestIp, ip)

        testIp = ip
        newTestPort = 80

        composeTestRule.onNodeWithText(testIp).performTextInput(newTestIp)
        composeTestRule.onNodeWithText(testPort.toString()).performTextInput(newTestPort.toString())

        Assert.assertNotEquals(newTestPort, port)
    }

    @Test
    fun testShowsConnectionIndicator() {
        composeTestRule.setContent { ConnectScreen({ _, _ -> }, State.Connecting) }
        composeTestRule.waitForIdle()
        composeTestRule
            .onNode(SemanticsMatcher.Companion.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo))
            .assertIsDisplayed()
    }

    @Test
    fun testShowsStateMessage() {
        val cause = IllegalStateException()
        composeTestRule.setContent { ConnectScreen({ _, _ -> }, State.Disconnected(cause)) }
        composeTestRule.onNodeWithText(cause.javaClass.simpleName).assertIsDisplayed()
    }
}