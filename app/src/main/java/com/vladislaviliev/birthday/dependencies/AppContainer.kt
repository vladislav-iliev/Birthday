package com.vladislaviliev.birthday.dependencies

import android.content.Context
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import com.vladislaviliev.birthday.kids.KidsApi
import com.vladislaviliev.birthday.networking.Client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppContainer {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher) = CoroutineScope(dispatcher)

    @Provides
    @Singleton
    fun provideKidsApi(): KidsApi = Client()

    @Provides
    @Singleton
    fun provideAvatarRepository(@ApplicationContext context: Context) = AvatarRepository(context, Dispatchers.IO)
}