package com.vladislaviliev.birthday.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.birthday.HiltTestActivity
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.kids.KidsApi
import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.screens.connect.ConnectScreenRoute
import com.vladislaviliev.birthday.screens.kid.KidScreenRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavControllerTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var testKidsApi: KidsApi

    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            Container(navController)
        }
    }

    @Test
    fun verifyStartDestinationIsConnectScreen() {
        Assert.assertEquals(ConnectScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }

    @Test
    fun testConnectNavigatesToKids() {
        val msg = Message("Johny", 1, Theme.PELICAN)
        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).emitMessage(msg) }

        composeTestRule.waitUntil(2000L) {
            !navController.backStack.any { it.destination.route == ConnectScreenRoute::class.qualifiedName }
        }
        Assert.assertEquals(KidScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }

    @Test
    fun testDisconnectGoesToConnectScreen() {
        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).emitMessage(Message("Johny", 1, Theme.PELICAN)) }
        composeTestRule.waitUntil {
            !navController.backStack.any { it.destination.route == ConnectScreenRoute::class.qualifiedName }
        }
        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).disconnect() }
        composeTestRule.waitUntil {
            !navController.backStack.any { it.destination.route == KidScreenRoute::class.qualifiedName }
        }
        Assert.assertEquals(ConnectScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }
}