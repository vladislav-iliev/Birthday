package com.vladislaviliev.birthday.dependencies

import android.content.Context
import com.vladislaviliev.birthday.networking.Api
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
import com.vladislaviliev.birthday.kid.avatar.Repository as AvatarRepository
import com.vladislaviliev.birthday.kid.avatar.RepositoryImpl as AvatarRepositoryImpl
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository
import com.vladislaviliev.birthday.kid.text.RepositoryImpl as TextRepositoryImpl

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
    fun provideApi(): Api = Client()

    @Provides
    @Singleton
    fun provideAvatarRepository(
        @ApplicationContext context: Context, scope: CoroutineScope, dispatcher: CoroutineDispatcher
    ): AvatarRepository = AvatarRepositoryImpl(context, scope, dispatcher)

    @Provides
    @Singleton
    fun provideTextRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher, api: Api): TextRepository =
        TextRepositoryImpl(scope, dispatcher, api)
}