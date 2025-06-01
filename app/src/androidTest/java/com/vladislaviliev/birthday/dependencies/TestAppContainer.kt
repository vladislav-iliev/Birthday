package com.vladislaviliev.birthday.dependencies

import android.content.Context
import com.vladislaviliev.birthday.networking.InMemoryApi
import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.kid.avatar.Repository
import com.vladislaviliev.birthday.kid.avatar.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

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
    fun provideKidsApi(): Api = InMemoryApi()

    // TODO swap with dummy
    @Provides
    @Singleton
    fun provideAvatarRepository(@ApplicationContext context: Context): Repository =
        RepositoryImpl(context, Dispatchers.IO)
}