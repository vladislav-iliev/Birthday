package com.vladislaviliev.birthday.screens.avatarPicker.camera

import android.app.Activity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.birthday.HiltTestActivity
import com.vladislaviliev.birthday.TestActivityResultRegistry
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.test.DummyAvatarRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CameraDestinationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var repository: AvatarRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun cameraDestination_whenPhotoTakenSuccessfully_savesUri() {

        val owner = object : ActivityResultRegistryOwner {
            override val activityResultRegistry = TestActivityResultRegistry(ActivityResult(Activity.RESULT_OK, null))
        }

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides owner) {
                Content { }
            }
        }

        composeTestRule.waitForIdle()
        Assert.assertNotNull((repository as DummyAvatarRepository).lastCopiedUri)
    }

    @Test
    fun cameraDestination_whenPhotoTakingCancelled_doesNotSaveUri() {

        val owner = object : ActivityResultRegistryOwner {
            override val activityResultRegistry =
                TestActivityResultRegistry(ActivityResult(Activity.RESULT_CANCELED, null))
        }

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides owner) {
                Content { }
            }
        }

        composeTestRule.waitForIdle()
        Assert.assertNull((repository as DummyAvatarRepository).lastCopiedUri)
    }
}