package com.vladislaviliev.birthday.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.kid.avatar.AvatarRepositoryImpl
import com.vladislaviliev.birthday.networking.NetworkingRepository
import com.vladislaviliev.birthday.networking.NetworkingRepositoryImpl

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
    fun provideNetworkingRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher): NetworkingRepository =
        NetworkingRepositoryImpl(scope, dispatcher)

    @Provides
    @Singleton
    fun provideAvatarRepository(
        @ApplicationContext context: Context, scope: CoroutineScope, dispatcher: CoroutineDispatcher
    ): AvatarRepository = AvatarRepositoryImpl(context, scope, dispatcher)
}