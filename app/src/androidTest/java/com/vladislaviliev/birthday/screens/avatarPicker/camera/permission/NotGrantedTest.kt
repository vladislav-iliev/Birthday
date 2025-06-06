package com.vladislaviliev.birthday.screens.avatarPicker.camera.permission

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.vladislaviliev.birthday.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPermissionsApi
@RunWith(AndroidJUnit4::class)
class NotGrantedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun content_whenCameraPermissionIsNotGranted_doesNotCallOnPermissionGrantedImmediately() {
        var onPermissionGrantedCalled = false

        composeTestRule.setContent { Content({}, { onPermissionGrantedCalled = true }) }
        composeTestRule
            .onNodeWithText(context.getString(R.string.using_camera_as_source_requires_permission))
            .assertIsDisplayed()
        Assert.assertFalse(onPermissionGrantedCalled)
    }
}