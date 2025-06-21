package com.vladislaviliev.birthday.dependencies

import com.vladislaviliev.birthday.test.DummyAvatarRepository
import com.vladislaviliev.birthday.test.DummyNetworkingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.networking.NetworkingRepository

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
    fun provideNetworkingRepository(): NetworkingRepository = DummyNetworkingRepository()


    @Provides
    @Singleton
    fun provideAvatarRepository(): AvatarRepository = DummyAvatarRepository()
}