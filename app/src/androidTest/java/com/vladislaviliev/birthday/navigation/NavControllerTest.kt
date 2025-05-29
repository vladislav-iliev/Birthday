package com.vladislaviliev.birthday.navigation

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.NavHost
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
import kotlinx.coroutines.Dispatchers
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

    lateinit var navController: TestNavHostController

    @Inject
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var testKidsApi: KidsApi

    @Before
    fun setup() {
        navController = createNavController(composeTestRule.activity)
        hiltRule.inject()
    }

    private fun createNavController(context: Context) = TestNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator()); navigatorProvider.addNavigator(DialogNavigator())
    }

    private fun setContentToAppDefault(navController: NavHostController) {
        composeTestRule.setContent { NavHost(navController, createAppGraph(navController)) }
    }

    @Test
    fun verifyStartDestinationIsConnectScreen() {
        setContentToAppDefault(navController)
        Assert.assertEquals(ConnectScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }

    @Test
    fun testConnectNavigatesToKids() {
        setContentToAppDefault(navController)

        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).emitMessage(Message("Johny", 1, Theme.PELICAN)) }

        composeTestRule.waitUntil {
            ConnectScreenRoute::class.qualifiedName != navController.currentDestination?.route
        }
        Assert.assertEquals(KidScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }

    @Test
    fun testDisconnectGoesToConnectScreen() {
        setContentToAppDefault(navController)

        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).emitMessage(Message("Johny", 1, Theme.PELICAN)) }
        composeTestRule.waitUntil {
            KidScreenRoute::class.qualifiedName == navController.currentDestination?.route
        }

        coroutineScope.launch { (testKidsApi as InMemoryKidsApi).disconnect() }
        composeTestRule.waitUntil {
            KidScreenRoute::class.qualifiedName != navController.currentDestination?.route
        }
        Assert.assertEquals(ConnectScreenRoute::class.qualifiedName, navController.currentDestination?.route)
    }
}