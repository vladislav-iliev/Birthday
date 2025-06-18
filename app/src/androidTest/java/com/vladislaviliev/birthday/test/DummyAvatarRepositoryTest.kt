package com.vladislaviliev.birthday.test

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DummyAvatarRepositoryTest { // Instrumented test - Bitmap requires Android runtime, the Android Uri too

    @Test
    fun initial_state_should_be_empty() = runTest {
        val repository = DummyAvatarRepository()

        println("Thread: " + Thread.currentThread().name)
        println("Thread: " + Thread.currentThread().name)
        println("Thread: " + Thread.currentThread().name)
        println("Thread: " + Thread.currentThread().name)
        println("Thread: " + Thread.currentThread().name)
        println("Thread: " + Thread.currentThread().name)

        Assert.assertNull(repository.bitmap.first())
        Assert.assertNull(repository.lastCopiedUri)
    }

    @Test
    fun copyFromUri_should_store_uri_and_update_bitmap() = runTest {
        val repository = DummyAvatarRepository()
        val testUri = Uri.parse("content://test/photo.jpg")

        repository.copyFromUri(testUri)

        Assert.assertEquals(testUri, repository.lastCopiedUri)
        Assert.assertNotNull(repository.bitmap.first())
    }

    @Test
    fun clearState_should_reset_all_state() = runTest {
        val repository = DummyAvatarRepository()
        val testUri = Uri.parse("content://test/photo.jpg")
        repository.copyFromUri(testUri)

        repository.clearState()

        Assert.assertNull(repository.bitmap.first())
        Assert.assertNull(repository.lastCopiedUri)
    }

    @Test
    fun multiple_copyFromUri_calls_should_update_lastCopiedUri() = runTest {
        val repository = DummyAvatarRepository()
        val firstUri = Uri.parse("content://test/first.jpg")
        val secondUri = Uri.parse("content://test/second.jpg")

        repository.copyFromUri(firstUri)
        repository.copyFromUri(secondUri)
        Assert.assertEquals(secondUri, repository.lastCopiedUri)
    }

    @Test
    fun bitmap_flow_should_emit_new_value_for_each_operation() = runTest {
        val repository = DummyAvatarRepository()

        Assert.assertNull(repository.bitmap.first())

        repository.copyFromUri(Uri.parse("content://test/photo.jpg"))
        Assert.assertNotNull(repository.bitmap.first())

        repository.clearState()
        Assert.assertNull(repository.bitmap.first())
        Assert.assertNull(repository.bitmap.first())
    }

    @Test
    fun created_bitmap_should_have_correct_dimensions() = runTest {
        val repository = DummyAvatarRepository()
        repository.copyFromUri(Uri.parse("content://test/photo.jpg"))
        val bitmap = repository.bitmap.value

        Assert.assertNotNull(bitmap)
        bitmap?.let {
            Assert.assertEquals(1, it.width)
            Assert.assertEquals(1, it.height)
        }
    }
}