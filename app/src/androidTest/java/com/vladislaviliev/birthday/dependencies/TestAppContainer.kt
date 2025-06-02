package com.vladislaviliev.birthday.dependencies

import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.test.DummyAvatarRepository
import com.vladislaviliev.birthday.test.DummyTextRepository
import com.vladislaviliev.birthday.test.LocalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import com.vladislaviliev.birthday.kid.avatar.Repository as AvatarRepository
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppContainer::class])
class TestAppContainer {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher) = CoroutineScope(dispatcher)

    @Provides
    @Singleton
    fun provideApi(): Api = LocalApi()

    @Provides
    @Singleton
    fun provideAvatarRepository(): AvatarRepository = DummyAvatarRepository()

    @Provides
    @Singleton
    fun provideTextRepository(): TextRepository = DummyTextRepository()
}