package com.vladislaviliev.birthday.screens.avatarPicker.camera.permission

import android.Manifest
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPermissionsApi
@RunWith(AndroidJUnit4::class)
class GrantedTest {

    @get:Rule(order = 0)
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun content_whenCameraPermissionIsAlreadyGranted_callsOnPermissionGranted() {

        var onPermissionGrantedCalled = false
        var onDismissRequestCalled = false

        composeTestRule.setContent {
            Content({ onDismissRequestCalled = true }, { onPermissionGrantedCalled = true })
        }

        Assert.assertTrue(onPermissionGrantedCalled)
        Assert.assertFalse(onDismissRequestCalled)
    }

}