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
import com.vladislaviliev.birthday.kid.avatar.Repository as AvatarRepository
import com.vladislaviliev.birthday.kid.avatar.RepositoryImpl as AvatarRepositoryImpl
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository
import com.vladislaviliev.birthday.kid.text.RepositoryImpl as TextRepositoryImpl
import com.vladislaviliev.birthday.networking.Repository as NetworkingRepository
import com.vladislaviliev.birthday.networking.RepositoryImpl as NetworkingRepositoryImpl

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

    @Provides
    @Singleton
    fun provideTextRepository(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        networkingRepository: NetworkingRepository
    ): TextRepository = TextRepositoryImpl(scope, dispatcher, networkingRepository)
}